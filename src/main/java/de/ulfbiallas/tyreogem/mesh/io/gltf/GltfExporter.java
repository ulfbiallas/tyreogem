package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Face;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.Vertex;
import de.ulfbiallas.tyreogem.mesh.io.Exporter;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfPrimitive.MeshPrimitiveMode;

public class GltfExporter implements Exporter {

    @Override
    public void exportMesh(Mesh mesh, File directory, String fileName) {
        exportMesh(mesh, directory, fileName, true);
    }

    public void exportMesh(Mesh mesh, File directory, String fileName, boolean embedTextures) {
        final File file = new File(directory.getAbsolutePath(), fileName + ".gltf");
        try {
            final FileWriter fileWriter = new FileWriter(file);
            exportMesh(mesh, fileWriter, directory, embedTextures);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void exportMesh(Mesh mesh, Writer writer, File directory, boolean embedTextures) throws IOException {
        final Mesh triangleMesh = mesh.triangulateByEarClipping();

        final GltfAsset gltfAsset = new GltfAsset();
        final GltfFile gltfFile = new GltfFile(gltfAsset);

        final Map<Material, List<Face>> facesByMaterial = triangleMesh.getFacesByMaterial();
        final List<Material> materials = triangleMesh.getMaterials();

        // ensure points/vertices/texCoords have the same indices
        int vertexIdx = 0;
        final List<Vec3d> pointsPerVertex = new ArrayList<>();
        final List<Vec3d> normalsPerVertex = new ArrayList<>();
        final List<Vec2d> uvPerVertex = new ArrayList<>();
        final Map<Material, List<Integer>> indicesByMaterial = new HashMap<>();
        for(Material material: materials) {
            indicesByMaterial.put(material, new ArrayList<Integer>());
            final List<Vertex> vertices = facesByMaterial.get(material).stream().flatMap(f -> f.getVertices().stream()).collect(Collectors.toList());
            for(Vertex vertex: vertices) {
                final int index = vertex.getPointIndex();
                pointsPerVertex.add(triangleMesh.getPoints().get(index));
                final Vec3d normal = vertex.getNormal();
                if(vertex.getNormal() != null) {
                    normalsPerVertex.add(normal);
                }
                final Vec2d uv = vertex.getTextureCoordinates();
                if(uv != null) {
                    final boolean flipX = false;
                    final boolean flipY = true;
                    final Vec2d uvFlipped = new Vec2d(flipX ? -uv.x : uv.x, flipY ? -uv.y : uv.y);
                    uvPerVertex.add(uvFlipped);
                }
                indicesByMaterial.get(material).add(vertexIdx);
                vertexIdx++;
            }
        }

        final int pointIndex = gltfFile.setPoints(pointsPerVertex);

        Map<String, Integer> attributes = new HashMap<>();
        attributes.put("POSITION", pointIndex);

        final int normalIndex = gltfFile.setNormals(normalsPerVertex);
        if(normalIndex > 0 && normalsPerVertex.size() == pointsPerVertex.size()) {
            attributes.put("NORMAL", normalIndex);
        }

        final int texCoordsIndex = gltfFile.setTextureCoordinates(uvPerVertex);
        if(texCoordsIndex > 0 && uvPerVertex.size() == pointsPerVertex.size()) {
            attributes.put("TEXCOORD_0", texCoordsIndex);
        }

        List<GltfPrimitive> gltfPrimitives = new ArrayList<>();
        for(Material material: materials) {
            Integer materialIndex = null;
            if(material != null && material.getMap_Kd() != null) {
                materialIndex = gltfFile.addTexture(material.getMap_Kd(), embedTextures);
                if(!embedTextures) {
                    final File target = new File(directory.getAbsolutePath(), material.getMap_Kd().getName());
                    Files.copy(material.getMap_Kd().toPath(), target.toPath());
                }
            }

            final int indicesIndex = gltfFile.setIndices(indicesByMaterial.get(material));
            GltfPrimitive gltfPrimitive = new GltfPrimitive(attributes, indicesIndex, MeshPrimitiveMode.TRIANGLES, materialIndex);
            gltfPrimitives.add(gltfPrimitive);
        }

        GltfMesh gltfMesh = new GltfMesh("defaultMesh", gltfPrimitives);
        gltfFile.addMesh(gltfMesh);

        final ObjectWriter objectWriter = new ObjectMapper().writerWithDefaultPrettyPrinter();
        try {
            final String json = objectWriter.writeValueAsString(gltfFile);
            writer.write(json);
            writer.close();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
