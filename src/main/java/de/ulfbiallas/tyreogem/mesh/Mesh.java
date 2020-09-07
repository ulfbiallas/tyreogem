package de.ulfbiallas.tyreogem.mesh;

import java.util.List;
import java.util.stream.Collectors;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Mesh {

    private final List<Vec3d> points;

    private final List<Face> faces;

    public Mesh(List<Vec3d> points, List<Face> faces) {
        this.points = points;
        this.faces = faces;
    }

    public List<Vec3d> getPoints() {
        return points;
    }

    public List<Face> getFaces() {
        return faces;
    }

    public Mesh reverseWinding() {
        return new Mesh(points, faces.stream().map(f -> f.reverseWinding()).collect(Collectors.toList()));
    }
}
