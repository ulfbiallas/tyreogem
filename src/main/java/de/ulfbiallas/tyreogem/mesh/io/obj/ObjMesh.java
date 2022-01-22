package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Face;
import de.ulfbiallas.tyreogem.mesh.Vertex;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.Mesh;

public class ObjMesh {

    private final List<Vec3d> vertices;
    private final List<Vec2d> textureCoordinates;
    private final List<Vec3d> vertexNormals;
    private final List<ObjFace> faces;
    private final Map<String, ObjMaterial> materials;

    public ObjMesh() {
        this.vertices = new ArrayList<Vec3d>();
        this.textureCoordinates = new ArrayList<>();
        this.vertexNormals = new ArrayList<>();
        this.faces = new ArrayList<>();
        this.materials = new HashMap<>();
    }

    public ObjMesh(Mesh mesh) {
        this.vertices = mesh.getPoints();
        this.textureCoordinates = new ArrayList<>();
        this.vertexNormals = new ArrayList<>();
        this.faces = new ArrayList<>();

        final List<ObjMaterial> objMaterials = mesh.getFaces().stream().map(face -> face.getMaterial()).filter(Objects::nonNull).distinct().map(m -> new ObjMaterial(m)).collect(Collectors.toList());
        this.materials = objMaterials.isEmpty() ? new HashMap<>() : objMaterials.stream().distinct().collect(Collectors.toMap(m -> m.getName(), m -> m));

        mesh.getFaces().forEach(face -> {
            final List<ObjFaceIndex> indices = new ArrayList<>();
            face.getVertices().forEach(vertex -> {
                Integer textureIndex = null;
                if(vertex.getTextureCoordinates() != null) {
                    this.textureCoordinates.add(vertex.getTextureCoordinates());
                    textureIndex = this.textureCoordinates.size()-1;
                }
                Integer normalIndex = null;
                if(vertex.getNormal() != null) {
                    this.vertexNormals.add(vertex.getNormal());
                    normalIndex = this.vertexNormals.size()-1;
                }
                indices.add(new ObjFaceIndex(vertex.getPointIndex(), textureIndex, normalIndex));
            });
            this.faces.add(new ObjFace(indices, face.getMaterial() != null ? face.getMaterial().getName() : ObjMaterial.NO_MATERIAL));
        });
    }

    public ObjMesh(List<Vec3d> vertices, List<ObjFace> faces) {
        this.vertices = vertices;
        this.textureCoordinates = new ArrayList<>();
        this.vertexNormals = new ArrayList<>();
        this.faces = faces;
        this.materials = new HashMap<>();
    }

    public ObjMesh(List<Vec3d> vertices, List<Vec2d> textureCoordinates, List<Vec3d> vertexNormals, List<ObjFace> faces, Map<String, ObjMaterial> materials) {
        this.vertices = vertices;
        this.textureCoordinates = textureCoordinates;
        this.vertexNormals = vertexNormals;
        this.faces = faces;
        this.materials = materials;
    }

    public Mesh toMesh() {
        final Map<String, Material> meshMaterials = new HashMap<>();
        this.materials.keySet().forEach(key -> {
            meshMaterials.put(key, this.materials.get(key).toMaterial());
        });

        List<Face> meshFaces = new ArrayList<>();
        faces.forEach(face -> {
            Material material = face.getMaterialName() != null ? meshMaterials.get(face.getMaterialName()) : null;
            List<Vertex> vertices = new ArrayList<>();
            face.getIndices().forEach(index -> {
                Vec3d faceNormal = index.getVertexNormalIndex() != null ? vertexNormals.get(index.getVertexNormalIndex()) : null;
                Vec2d faceTextureCoordinates = index.getTextureCoordinatesIndex() != null ? textureCoordinates.get(index.getTextureCoordinatesIndex()) : null;
                vertices.add(new Vertex(index.getVertexIndex(), faceNormal, faceTextureCoordinates));
            });
            meshFaces.add(new Face(vertices, material));
        });
        return new Mesh(vertices, meshFaces);
    }

    public List<Vec3d> getVertices() {
        return vertices;
    }

    public List<Vec2d> getTextureCoordinates() {
        return textureCoordinates;
    }

    public List<Vec3d> getVertexNormals() {
        return vertexNormals;
    }

    public List<ObjFace> getFaces() {
        return faces;
    }

    public Map<String, ObjMaterial> getMaterials() {
        return materials;
    }

}
