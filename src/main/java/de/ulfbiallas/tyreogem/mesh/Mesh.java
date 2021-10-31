package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
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

    public Mesh(List<Mesh> meshes) {
        this.points = new ArrayList<>();
        this.faces = new ArrayList<>();

        for(Mesh mesh: meshes) {
            final int pointOffset = this.points.size();
            this.points.addAll(mesh.getPoints());
            this.faces.addAll(mesh.faces.stream().map(face -> new Face(face.getVertices().stream().map(vertex -> new Vertex(vertex.getPointIndex()+pointOffset, vertex.getNormal(), vertex.getTextureCoordinates())).collect(Collectors.toList()), face.getMaterial())).collect(Collectors.toList()));
        }
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

    public Vec3d getCentroidOfFace(Face face) {
        final List<Vec3d> pointsOfFace = face.getVertices().stream().map(v -> points.get(v.getPointIndex())).collect(Collectors.toList());
        return Vec3d.arithmeticMean(pointsOfFace);
    }

    public Vec3d getFaceNormal(Face face) {
        // Assumptions:
        // - the face is planar
        // - consecutive edges are not parallel

        final Vec3d v0 = points.get(face.getVertices().get(0).getPointIndex());
        final Vec3d v1 = points.get(face.getVertices().get(1).getPointIndex());
        final Vec3d v2 = points.get(face.getVertices().get(2).getPointIndex());

        final Vec3d a = v1.sub(v0);
        final Vec3d b = v2.sub(v0);

        return a.cross(b).normalize();
    }

}
