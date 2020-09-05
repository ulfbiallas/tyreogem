package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.io.Importer;

public class ObjImporter implements Importer {

    @Override
    public Mesh importMesh(File directory, String fileName) {
        final ObjFileDescriptor descriptor = new ObjFileDescriptor(directory, fileName);
        final ObjMesh objMesh = importObj(descriptor);
        return objMesh.toMesh();
    }

    public static ObjMesh importObj(ObjFileDescriptor objFileDescriptor) {
        File objFile = objFileDescriptor.getObjFile();
        File mtlFile = objFileDescriptor.getMtlFile();

        String lineRaw;
        String materialName = null;
        final List<Vec3d> vertices = new ArrayList<>();
        final List<Vec2d> textureCoordinates = new ArrayList<>();
        final List<Vec3d> vertexNormals = new ArrayList<>();
        final List<ObjFace> faces = new ArrayList<>();
        final Map<String, ObjMaterial> materials = mtlFile != null ? importMtl(objFileDescriptor) : new HashMap<>();

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(objFile));
            try {
                while((lineRaw = reader.readLine()) != null) {
                    // Replace tabs and multiple spaces with single spaces ("   " -> " ")
                    final String line = lineRaw.replaceAll("\t", " ").trim().replaceAll(" +", " ");

                    if(line.startsWith("usemtl ")) {
                        final String[] components = line.split(" ");
                        materialName = components[1];
                    }

                    if(line.startsWith("v ")) {
                        final String[] components = line.split(" ");
                        final double x = Double.parseDouble(components[1]);
                        final double y = Double.parseDouble(components[2]);
                        final double z = Double.parseDouble(components[3]);
                        vertices.add(new Vec3d(x, y, z));
                    }

                    if(line.startsWith("vt ")) {
                        final String[] components = line.split(" ");
                        final double u = Double.parseDouble(components[1]);
                        final double v = Double.parseDouble(components[2]);
                        textureCoordinates.add(new Vec2d(u, v));
                    }

                    if(line.startsWith("vn ")) {
                        final String[] components = line.split(" ");
                        final double x = Double.parseDouble(components[1]);
                        final double y = Double.parseDouble(components[2]);
                        final double z = Double.parseDouble(components[3]);
                        vertexNormals.add(new Vec3d(x, y, z));
                    }

                    if(line.startsWith("f")) {
                        final String[] components = line.split(" ");
                        final List<ObjFaceIndex> indices = new ArrayList<>();
                        for(int k=1; k<components.length; ++k) {
                            final ObjFaceIndex fIdx = parseFaceIndex(components[k]);
                            final ObjFaceIndex normalizyedFIdx = normalizeFaceIndex(fIdx, vertices.size(), textureCoordinates.size(), vertexNormals.size());
                            indices.add(normalizyedFIdx);
                        }
                        ObjFace face = new ObjFace(indices, materialName);
                        faces.add(face);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        final ObjMesh mesh = new ObjMesh(vertices, textureCoordinates, vertexNormals, faces, materials);
        return mesh;
    }

    private static Map<String, ObjMaterial> importMtl(ObjFileDescriptor objFileDescriptor) {
        Map<String, ObjMaterial> materials = new HashMap<>();

        ObjMaterial material = null;
        String materialName;
        int illum; // illumination model
        Vec3d Ka; // ambient reflectivity (Default 0.2 0.2 0.2)
        Vec3d Kd; // diffuse reflectivity (Default 0.8 0.8 0.8)
        Vec3d Ks; // specular reflectivity
        Vec3d Ke; // emission
        Vec3d Tf; // transmission filter
        double Tr; // transparency (Tr = 1.0 - d) (Default: 1.0 = not transparent)
        double d; // dissolve (d = 1.0 - Tr) (Default: 0.0 = not transparent)
        double Ns; // specular exponent (Default 0.0)
        double Ni; // optical density
        double sharpness; // sharpness of the reflections
        
        String map_Ka; // ambient texture
        String map_Kd; // diffuse texture
        String map_d; // dissolve texture
        String map_bump; // bump map texture

        String lineRaw;
        try {
            final BufferedReader reader = new BufferedReader(new FileReader(objFileDescriptor.getMtlFile()));
            try {
                while((lineRaw = reader.readLine()) != null) {
                    // Replace multiple spaces with single spaces ("   " -> " ")
                    final String line = lineRaw.trim().replaceAll(" +", " ");

                    if(line.startsWith("newmtl ")) {
                        final String[] components = line.split(" ");
                        materialName = components[1];

                        if(material != null) {
                            materials.put(material.getName(), material);
                        }
                        material = new ObjMaterial();
                        material.setName(materialName);
                        material.setDirectory(objFileDescriptor.getDirectoryPath());
                    }
                    if(line.startsWith("illum ")) {
                        final String[] components = line.split(" ");
                        illum = Integer.parseInt(components[1]);
                        material.setIllum(illum);
                    }
                    if(line.startsWith("Ns ")) {
                        final String[] components = line.split(" ");
                        Ns = Double.parseDouble(components[1]);
                        material.setNs(Ns);
                    }
                    if(line.startsWith("Ni ")) {
                        final String[] components = line.split(" ");
                        Ni = Double.parseDouble(components[1]);
                        material.setNi(Ni);
                    }
                    if(line.startsWith("sharpness ")) {
                        final String[] components = line.split(" ");
                        sharpness = Double.parseDouble(components[1]);
                        material.setSharpness(sharpness);
                    }
                    if(line.startsWith("Tr ")) {
                        final String[] components = line.split(" ");
                        Tr = Double.parseDouble(components[1]);
                        material.setTr(Tr);
                    }
                    if(line.startsWith("d ")) {
                        final String[] components = line.split(" ");
                        d = Double.parseDouble(components[1]);
                        material.setD(d);
                    }
                    if(line.startsWith("Ka ")) {
                        final String[] components = line.split(" ");
                        final double r = Double.parseDouble(components[1]);
                        final double g = Double.parseDouble(components[2]);
                        final double b = Double.parseDouble(components[3]);
                        Ka = new Vec3d(r, g, b);
                        material.setKa(Ka);
                    }
                    if(line.startsWith("Kd ")) {
                        final String[] components = line.split(" ");
                        final double r = Double.parseDouble(components[1]);
                        final double g = Double.parseDouble(components[2]);
                        final double b = Double.parseDouble(components[3]);
                        Kd = new Vec3d(r, g, b);
                        material.setKd(Kd);
                    }
                    if(line.startsWith("Ks ")) {
                        final String[] components = line.split(" ");
                        final double r = Double.parseDouble(components[1]);
                        final double g = Double.parseDouble(components[2]);
                        final double b = Double.parseDouble(components[3]);
                        Ks = new Vec3d(r, g, b);
                        material.setKs(Ks);
                    }
                    if(line.startsWith("Ke ")) {
                        final String[] components = line.split(" ");
                        final double r = Double.parseDouble(components[1]);
                        final double g = Double.parseDouble(components[2]);
                        final double b = Double.parseDouble(components[3]);
                        Ke = new Vec3d(r, g, b);
                        material.setKe(Ke);
                    }
                    if(line.startsWith("Tf ")) {
                        final String[] components = line.split(" ");
                        final double r = Double.parseDouble(components[1]);
                        final double g = Double.parseDouble(components[2]);
                        final double b = Double.parseDouble(components[3]);
                        Tf = new Vec3d(r, g, b);
                        material.setTf(Tf);
                    }
                    if(line.startsWith("map_Ka ")) {
                        final String[] components = line.split(" ");
                        final int argCount = components.length;
                        map_Ka = String.join(" ", Arrays.copyOfRange(components, 1, argCount));
                        material.setMap_Ka(map_Ka);
                    }
                    if(line.startsWith("map_Kd ")) {
                        final String[] components = line.split(" ");
                        final int argCount = components.length;
                        map_Kd = String.join(" ", Arrays.copyOfRange(components, 1, argCount));
                        material.setMap_Kd(map_Kd);
                    }
                    if(line.startsWith("map_d ")) {
                        final String[] components = line.split(" ");
                        final int argCount = components.length;
                        map_d = String.join(" ", Arrays.copyOfRange(components, 1, argCount));
                        material.setMap_d(map_d);
                    }
                    if(line.startsWith("map_bump ")) {
                        final String[] components = line.split(" ");
                        final int argCount = components.length;
                        map_bump = String.join(" ", Arrays.copyOfRange(components, 1, argCount));
                        material.setMap_bump(map_bump);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(material != null) {
                    materials.put(material.getName(), material);
                }
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return materials;
    }

    /**
     * Face indices are of the shape "i" or "i/j/k" where i is the index of the vertex,
     * j is the index of the texture coordinate and k the index of a vertex normal.
     * This function returns just the index of the vertex.
     * (Note: OBJ indices start counting from 1 instead of 0)
     */
    private static ObjFaceIndex parseFaceIndex(String faceIndex) {
        final Integer vertexIndex = faceIndex.contains("/")
            ? Integer.parseInt(faceIndex.split("/")[0]) - 1
            : Integer.parseInt(faceIndex) - 1;

        final Integer textureCoordinatesIndex = faceIndex.contains("/")
            ? Integer.parseInt(faceIndex.split("/")[1]) - 1
            : null;

            /*// TODO import org.apache.commons.lang3.StringUtils;
        final Integer vertexNormalIndex = StringUtils.countMatches(faceIndex, "/") == 2
            ? Integer.parseInt(faceIndex.split("/")[2]) - 1
            : null;
            */
            final Integer vertexNormalIndex = null;

        return new ObjFaceIndex(vertexIndex, textureCoordinatesIndex, vertexNormalIndex);
    }

    /**
     * Relative indices (negative numbers) will be converted to absolute indices.
     * (In the case of relative indices we have to add the 1 again which we subtracted in parseFaceIndex())
     */
    private static ObjFaceIndex normalizeFaceIndex(ObjFaceIndex fIdx, int vertexCount, int textureCoordinatesCount, int vertexNormalCount) {
        final Integer vertexIndex = fIdx.getVertexIndex() != null ? (
            fIdx.getVertexIndex() < 0 ? vertexCount + fIdx.getVertexIndex() + 1 : fIdx.getVertexIndex()
        ) : null;

        final Integer textureCoordinatesIndex = fIdx.getTextureCoordinatesIndex() != null ? (
            fIdx.getTextureCoordinatesIndex() < 0 ? textureCoordinatesCount + fIdx.getTextureCoordinatesIndex() + 1: fIdx.getTextureCoordinatesIndex()
        ) : null;

        final Integer vertexNormalIndex = fIdx.getVertexNormalIndex() != null ? (
            fIdx.getVertexNormalIndex() < 0 ? vertexNormalCount + fIdx.getVertexNormalIndex() + 1 : fIdx.getVertexNormalIndex()
        ) : null;

        return new ObjFaceIndex(vertexIndex, textureCoordinatesIndex, vertexNormalIndex);
    }

}
