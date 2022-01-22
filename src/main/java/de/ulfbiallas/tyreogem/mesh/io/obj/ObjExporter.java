package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
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

        final FileWriter objFileWriter = new FileWriter(descriptor.getObjFile());
        writeObjFile(objMesh, objFileWriter);

        if(!objMesh.getMaterials().isEmpty()) {
            final FileWriter mtlFileWriter = new FileWriter(descriptor.getMtlFile());
            writeMtlFile(objMesh, mtlFileWriter);
        }
    }

    private String createMtlLine(String name, Vec3d value) {
        if(value != null) {
            return name + " " + value.x + " " + value.y + " " + value.z +"\n";
        } else {
            return "";
        }
    }

    private String createMtlLine(String name, double value) {
        final String roundedValue = formatDouble(value);
        return name + " " + roundedValue +"\n";
    }

    private String createMtlLine(String name, int value) {
        return name + " " + value +"\n";
    }

    private String createMtlLine(String name, String value) {
        return name + " " + value +"\n";
    }

    public void writeMtlFile(ObjMesh objMesh, Writer writer) throws IOException {
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

        try {
            writer.write(mtlFileBuilder.toString());
        } finally {
            writer.flush();
            writer.close();
        }
    }

    public void writeObjFile(ObjMesh mesh, Writer writer) throws IOException {
        try {

            for(Vec3d v: mesh.getVertices()) {
                final String roundedX = formatDouble(v.x);
                final String roundedY = formatDouble(v.y);
                final String roundedZ = formatDouble(v.z);
                writer.write("v " + roundedX + " " + roundedY + " " + roundedZ + "\n");
            }

            for(Vec2d uv: mesh.getTextureCoordinates()) {
                writer.write("vt " + uv.x + " " + uv.y + "\n");
            }

            for(Vec3d n: mesh.getVertexNormals()) {
                writer.write("vn " + n.x + " " + n.y + " " + n.z + "\n");
            }

            final Map<String, List<ObjFace>> facesByMaterialName = mesh.getFaces().stream().collect(Collectors.groupingBy(ObjFace::getMaterialName));

            final List<String> materialNames =  Stream.concat(mesh.getMaterials().keySet().stream(), Arrays.asList(ObjMaterial.NO_MATERIAL).stream()).collect(Collectors.toList());
            for(String materialName: materialNames) {
                if(!materialName.equals(ObjMaterial.NO_MATERIAL)) {
                    writer.write("usemtl " + materialName + "\n");
                }
                if(facesByMaterialName.containsKey(materialName)) {
                    for(ObjFace face: facesByMaterialName.get(materialName)) {
                        StringBuilder faceString = new StringBuilder();
                        faceString.append("f");
                        for(ObjFaceIndex fi: face.getIndices()) {
                            faceString.append(" ");
                            faceString.append(createObjIndex(fi.getVertexIndex()));
                            if(fi.getTextureCoordinatesIndex() != null || fi.getVertexNormalIndex() != null) {
                                faceString.append("/");
                                faceString.append(createObjIndex(fi.getTextureCoordinatesIndex()));
                                if(fi.getVertexNormalIndex() != null) {
                                    faceString.append("/");
                                    faceString.append(createObjIndex(fi.getVertexNormalIndex()));
                                }
                            }
                        }
                        faceString.append("\n");
                        writer.write(faceString.toString());
                    }
                }
            }

        } finally {
            writer.flush();
            writer.close();
        }
    }

    private String formatDouble(double value) {
        NumberFormat formatter = NumberFormat.getNumberInstance(Locale.ROOT);
        formatter.setMinimumFractionDigits(1);
        formatter.setMaximumFractionDigits(3);
        return formatter.format(value);
        //return String.format(Locale.US, "%.3f", value);
    }

    private String createObjIndex(Integer vertexIndex) {
        if(vertexIndex != null) {
            int objIndex = vertexIndex + 1;
            return "" + objIndex;
        }
        return "";
    }

}
