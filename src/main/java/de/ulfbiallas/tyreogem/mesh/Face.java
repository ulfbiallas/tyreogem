package de.ulfbiallas.tyreogem.mesh;

import java.util.List;

public class Face {

    private final List<FaceVertex> vertices;

    private final Material material;

    public Face(List<FaceVertex> vertices, Material material) {
        this.vertices = vertices;
        this.material = material;
    }

    public Face(List<FaceVertex> vertices) {
        this(vertices, null);
    }

    public List<FaceVertex> getVertices() {
        return vertices;
    }

    public Material getMaterial() {
        return material;
    }

}
