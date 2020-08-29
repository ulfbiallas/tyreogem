package de.ulfbiallas.tyreogem.mesh;

import java.util.List;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Mesh {

    private final List<Vec3d> vertices;

    private final List<Face> faces;

    public Mesh(List<Vec3d> vertices, List<Face> faces) {
        this.vertices = vertices;
        this.faces = faces;
    }

    public List<Vec3d> getVertices() {
        return vertices;
    }

    public List<Face> getFaces() {
        return faces;
    }

}
