package de.ulfbiallas.tyreogem.mesh;

import java.util.List;

public class Face {

    private final List<Vertex> vertices;

    private final Material material;

    public Face(List<Vertex> vertices, Material material) {
        this.vertices = vertices;
        this.material = material;
    }

    public Face(List<Vertex> vertices) {
        this(vertices, null);
    }

    public List<Vertex> getVertices() {
        return vertices;
    }

    public Material getMaterial() {
        return material;
    }

}
