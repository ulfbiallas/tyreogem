package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
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

    public Face reverseWinding() {
        final List<Vertex> verticesReversed = new ArrayList<>();
        for(int k=vertices.size()-1; k>=0; k--) {
            verticesReversed.add(vertices.get(k));
        }
        return new Face(verticesReversed, material != null ? material.clone() : null);
    }
}
