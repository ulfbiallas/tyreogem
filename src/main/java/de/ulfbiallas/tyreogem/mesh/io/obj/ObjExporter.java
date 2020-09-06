package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.io.Exporter;

public class ObjExporter implements Exporter {

    @Override
    public void exportMesh(Mesh mesh, File directory, String fileName) {
        try {
            exportObj(new ObjMesh(mesh), directory, fileName);
        } catch (IOException e) {
            throw new RuntimeException("Mesh could not be exported to OBJ/MTL!", e);
        }
    }

    public void exportObj(ObjMesh objMesh, File directory, String fileName) throws IOException {
        final ObjFileDescriptor descriptor = new ObjFileDescriptor(directory, fileName);
        writeObjFile(objMesh, descriptor.getObjFile());
        writeMtlFile(objMesh, descriptor.getMtlFile());
    }

    private String createMtlLine(String name, Vec3d value) {
        return name + " " + value.x + " " + value.y + " " + value.z +"\n";
    }

    private String createMtlLine(String name, double value) {
        return name + " " + value +"\n";
    }

    private String createMtlLine(String name, int value) {
        return name + " " + value +"\n";
    }

    private String createMtlLine(String name, String value) {
        return name + " " + value +"\n";
    }

    private void writeMtlFile(ObjMesh objMesh, File mtlFile) throws IOException {
        final StringBuilder mtlFileBuilder = new StringBuilder();
        for(ObjMaterial material: objMesh.getMaterials().values()) {
            mtlFileBuilder.append("newmtl " + material.getName() + "\n");
            mtlFileBuilder.append(createMtlLine("Ka", material.getKa()));
            mtlFileBuilder.append(createMtlLine("Kd", material.getKd()));
            mtlFileBuilder.append(createMtlLine("Ks", material.getKs()));
            mtlFileBuilder.append(createMtlLine("d", material.getD()));
            mtlFileBuilder.append(createMtlLine("sharpness", material.getSharpness()));
            mtlFileBuilder.append(createMtlLine("illum", material.getIllum()));
            mtlFileBuilder.append(createMtlLine("map_Ka", material.getMap_Ka()));
            mtlFileBuilder.append(createMtlLine("map_Kd", material.getMap_Kd()));
            mtlFileBuilder.append(createMtlLine("map_d", material.getMap_d()));
            mtlFileBuilder.append("\n");
        }

        final FileWriter fileWriter = new FileWriter(mtlFile);
        try {
            fileWriter.write(mtlFileBuilder.toString());
        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private void writeObjFile(ObjMesh mesh, File fileToExport) throws IOException {
        final FileWriter fileWriter = new FileWriter(fileToExport);
        try {

            for(Vec3d v: mesh.getVertices()) {
                fileWriter.write("v " + v.x + " " + v.y + " " + v.z + "\n");
            }

            for(Vec2d uv: mesh.getTextureCoordinates()) {
                fileWriter.write("vt " + uv.x + " " + uv.y + "\n");
            }

            for(Vec3d n: mesh.getVertexNormals()) {
                fileWriter.write("vn " + n.x + " " + n.y + " " + n.z + "\n");
            }

            final Map<String, List<ObjFace>> facesByMaterialName = mesh.getFaces().stream().collect(Collectors.groupingBy(ObjFace::getMaterialName));

            final List<String> materialNames =  Stream.concat(mesh.getMaterials().keySet().stream(), Arrays.asList(ObjMaterial.NO_MATERIAL).stream()).collect(Collectors.toList());
            for(String materialName: materialNames) {
                if(!materialName.equals(ObjMaterial.NO_MATERIAL)) {
                    fileWriter.write("usemtl " + materialName + "\n");
                }
                if(facesByMaterialName.containsKey(materialName)) {
                    for(ObjFace face: facesByMaterialName.get(materialName)) {
                        StringBuilder faceString = new StringBuilder();
                        faceString.append("f ");
                        for(ObjFaceIndex fi: face.getIndices()) {
                            faceString.append(createObjIndex(fi.getVertexIndex()));
                            faceString.append("/");
                            faceString.append(createObjIndex(fi.getTextureCoordinatesIndex()));
                            faceString.append("/");
                            faceString.append(createObjIndex(fi.getVertexNormalIndex()));
                            faceString.append(" ");
                        }
                        faceString.append("\n");
                        fileWriter.write(faceString.toString());
                    }
                }
            }

        } finally {
            fileWriter.flush();
            fileWriter.close();
        }
    }

    private String createObjIndex(Integer vertexIndex) {
        if(vertexIndex != null) {
            int objIndex = vertexIndex + 1;
            return "" + objIndex;
        }
        return "";
    }

}
