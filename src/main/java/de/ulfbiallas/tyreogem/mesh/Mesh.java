package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.core.spatial.Plane;
import de.ulfbiallas.tyreogem.core.spatial.Triangle;

public class Mesh {

    private final List<Vec3d> points;

    private final List<Face> faces;

    private final Map<Material, List<Face>> facesByMaterial = new HashMap<>(); // Faces without materials have the key null.

    public Mesh(List<Vec3d> points, List<Face> faces) {
        this.points = points;
        this.faces = faces;
        groupFacesByMaterial();
    }

    public Mesh(List<Mesh> meshes) {
        this.points = new ArrayList<>();
        this.faces = new ArrayList<>();

        for(Mesh mesh: meshes) {
            final int pointOffset = this.points.size();
            this.points.addAll(mesh.getPoints());
            this.faces.addAll(mesh.faces.stream().map(face -> new Face(face.getVertices().stream().map(vertex -> new Vertex(vertex.getPointIndex()+pointOffset, vertex.getNormal(), vertex.getTextureCoordinates())).collect(Collectors.toList()), face.getMaterial())).collect(Collectors.toList()));
        }
        groupFacesByMaterial();
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

    public Mesh mirrorByPoint(Vec3d point) {
        final List<Vec3d> mirroredPoints = getPoints().stream().map(p -> p.mirrorByPoint(point)).collect(Collectors.toList());
        final List<Face> facesWithMirroredNormals = getFaces().stream().map(face -> new Face(face.getVertices().stream().map(vertex ->new Vertex(vertex.getPointIndex(), vertex.getNormal().negate(), vertex.getTextureCoordinates())).collect(Collectors.toList()), face.getMaterial())).collect(Collectors.toList());
        return new Mesh(mirroredPoints, facesWithMirroredNormals).reverseWinding();
    }

    public Mesh mirrorByPlane(Plane plane) {
        final List<Vec3d> mirroredPoints = getPoints().stream().map(p -> p.mirrorByPlane(plane)).collect(Collectors.toList());
        final List<Face> facesWithMirroredNormals = getFaces().stream().map(face -> new Face(face.getVertices().stream().map(vertex ->new Vertex(vertex.getPointIndex(), vertex.getNormal().mirrorByPlane(new Plane(Vec3d.zero(), plane.getNormal())), vertex.getTextureCoordinates())).collect(Collectors.toList()), face.getMaterial())).collect(Collectors.toList());
        return new Mesh(mirroredPoints, facesWithMirroredNormals).reverseWinding();
    }

    public Mesh triangulateByEarClipping() {
        final List<Face> triangles = new ArrayList<>();
        for(Face face: faces) {
            int vertexCount = face.getVertices().size();
            if(vertexCount < 3) {
                // Invalid face. Will be discarded
            } else if(vertexCount == 3) {
                triangles.add(face);
            } else {
                List<Vertex> vertices = face.getVertices().stream().collect(Collectors.toList());
                while(vertices.size() >= 3) {
                    int earOffset = getEarOffset(vertices);
                    triangles.add(new Face(
                        Arrays.asList(
                            vertices.get((earOffset + 0) % vertices.size()),
                            vertices.get((earOffset + 1) % vertices.size()),
                            vertices.get((earOffset + 2) % vertices.size())
                        ),   
                        face.getMaterial()
                    ));
                    vertices.remove((earOffset + 1) % vertices.size()); // clip ear
                }
            }
        }
        return new Mesh(this.points, triangles);
    }

    public Map<Material, List<Face>> getFacesByMaterial() {
        return facesByMaterial;
    }

    public List<Material> getMaterials() {
        final List<Material> availableMaterials = facesByMaterial.keySet().stream().collect(Collectors.toList());
        return availableMaterials;
    }

    // Returns the first triangle that does not contain any of the other vertices
    private int getEarOffset(List<Vertex> vertices) {
        int offset = 0;
        Triangle triangle = getTriangleAtOffset(vertices, offset);
        while(containsOtherPoints(triangle, vertices, offset)) {
            offset++;
            triangle = getTriangleAtOffset(vertices, offset);
        }
        return offset;
    }

    private boolean containsOtherPoints(Triangle triangle, List<Vertex> vertices, int offset) {
        for(int k=0; k<offset; ++k) {
            if(triangle.isPointInside(this.points.get(vertices.get(k).getPointIndex()))) {
                return true;
            }
        }
        for(int k=offset+3; k<vertices.size(); ++k) {
            if(triangle.isPointInside(this.points.get(vertices.get(k).getPointIndex()))) {
                return true;
            }
        }
        return false;
    }

    private Triangle getTriangleAtOffset(List<Vertex> vertices, int offset) {
        Vec3d p1 = this.points.get(vertices.get((offset + 0) % vertices.size()).getPointIndex());
        Vec3d p2 = this.points.get(vertices.get((offset + 1) % vertices.size()).getPointIndex());
        Vec3d p3 = this.points.get(vertices.get((offset + 2) % vertices.size()).getPointIndex());
        return new Triangle(p1, p2, p3);
    }

    private void groupFacesByMaterial() {
        for(Face face: getFaces()) {
            final Material material = face.getMaterial();
            if(!facesByMaterial.containsKey(material)) {
                facesByMaterial.put(material, new ArrayList<Face>());
            }
            facesByMaterial.get(material).add(face);
        }
    }

}
