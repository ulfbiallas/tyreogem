package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.core.spatial.Plane;

public class MeshTest {

    private static final Vec3d v0 = new Vec3d(0.0, 0.0, 0.0);
    private static final Vec3d v1 = new Vec3d(1.0, 0.0, 0.0);
    private static final Vec3d v2 = new Vec3d(1.0, 0.0, 1.0);
    private static final Vec3d v3 = new Vec3d(0.0, 0.0, 1.0);

    @Test
    public void test_constructor() {
        final Mesh mesh = createMesh();

        Assert.assertEquals(4, mesh.getPoints().size());
        Assert.assertEquals(v0, mesh.getPoints().get(0));
        Assert.assertEquals(v1, mesh.getPoints().get(1));
        Assert.assertEquals(v2, mesh.getPoints().get(2));
        Assert.assertEquals(v3, mesh.getPoints().get(3));

        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(2, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(1, mesh.getFaces().get(0).getVertices().get(1).getPointIndex());
        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(2).getPointIndex());

        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());
        Assert.assertEquals(0, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().get(1).getPointIndex());
        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(2).getPointIndex());

        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(1.0, 1.0), mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(0.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());
    }

    @Test
    public void test_constructor_mergeMeshes() {
        final Mesh mesh1 = createMesh();

        final Vec3d v4 = new Vec3d(1.0, 2.0, 0.0);
        final Vec3d v5 = new Vec3d(1.0, 2.0, 1.0);
        final Vec3d v6 = new Vec3d(0.0, 2.0, 1.0);
        final Vertex vertex0 = new Vertex(0, new Vec3d(0.0, -1.0, 0.0), new Vec2d(0.0, 0.0));
        final Vertex vertex1 = new Vertex(1, new Vec3d(0.0, -1.0, 0.0), new Vec2d(1.0, 0.0));
        final Vertex vertex2 = new Vertex(2, new Vec3d(0.0, -1.0, 0.0), new Vec2d(1.0, 1.0));
        final Face face0 = new Face(Arrays.asList(vertex0, vertex1, vertex2));
        final Mesh mesh2 = new Mesh(Arrays.asList(v4, v5, v6), Arrays.asList(face0));

        final Mesh mesh = new Mesh(Arrays.asList(mesh1, mesh2));

        Assert.assertEquals(7, mesh.getPoints().size());
        Assert.assertEquals(v0, mesh.getPoints().get(0));
        Assert.assertEquals(v1, mesh.getPoints().get(1));
        Assert.assertEquals(v2, mesh.getPoints().get(2));
        Assert.assertEquals(v3, mesh.getPoints().get(3));
        Assert.assertEquals(v4, mesh.getPoints().get(4));
        Assert.assertEquals(v5, mesh.getPoints().get(5));
        Assert.assertEquals(v6, mesh.getPoints().get(6));

        Assert.assertEquals(3, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(2).getVertices().size());

        Assert.assertEquals(2, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(0, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(4, mesh.getFaces().get(2).getVertices().get(0).getPointIndex());

        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec3d(0.0, -1.0, 0.0), mesh.getFaces().get(2).getVertices().get(0).getNormal());
    }

    @Test
    public void test_reverseWinding() {
        final Mesh mesh = createMesh();

        final Mesh meshWithReversedWinding = mesh.reverseWinding();

        Assert.assertEquals(4, meshWithReversedWinding.getPoints().size());
        Assert.assertEquals(v0, meshWithReversedWinding.getPoints().get(0));
        Assert.assertEquals(v1, meshWithReversedWinding.getPoints().get(1));
        Assert.assertEquals(v2, meshWithReversedWinding.getPoints().get(2));
        Assert.assertEquals(v3, meshWithReversedWinding.getPoints().get(3));

        Assert.assertEquals(0, meshWithReversedWinding.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(1, meshWithReversedWinding.getFaces().get(0).getVertices().get(1).getPointIndex());
        Assert.assertEquals(2, meshWithReversedWinding.getFaces().get(0).getVertices().get(2).getPointIndex());

        Assert.assertEquals(2, meshWithReversedWinding.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(3, meshWithReversedWinding.getFaces().get(1).getVertices().get(1).getPointIndex());
        Assert.assertEquals(0, meshWithReversedWinding.getFaces().get(1).getVertices().get(2).getPointIndex());
    }

    @Test
    public void test_getCentroid() {
        final Mesh mesh = createMesh();
        final Vec3d centroid = mesh.getCentroidOfFace(mesh.getFaces().get(0));
        Assert.assertEquals(0.6666666, centroid.x, 0.00001);
        Assert.assertEquals(0.0000000, centroid.y, 0.00001);
        Assert.assertEquals(0.3333333, centroid.z, 0.00001);
    }

    @Test
    public void test_getFaceNormal() {
        final Mesh mesh = createMesh();
        final Vec3d normal = mesh.getFaceNormal(mesh.getFaces().get(0));
        Assert.assertEquals(0.0, normal.x, 0.00001);
        Assert.assertEquals(1.0, normal.y, 0.00001);
        Assert.assertEquals(0.0, normal.z, 0.00001);

        final Mesh meshRewinded = mesh.reverseWinding();
        final Vec3d normalRewinded = meshRewinded.getFaceNormal(meshRewinded.getFaces().get(0));
        Assert.assertEquals(0.0, normalRewinded.x, 0.00001);
        Assert.assertEquals(-1.0, normalRewinded.y, 0.00001);
        Assert.assertEquals(0.0, normalRewinded.z, 0.00001);    
    }

    @Test
    public void test_mirrorByPoint() {
        Mesh mesh = createMesh();
        Vec3d point = new Vec3d(1.0, 1.0, 1.0);
        Mesh meshMirrored = mesh.mirrorByPoint(point);

        Assert.assertEquals(mesh.getPoints().get(0), meshMirrored.getPoints().get(0).mirrorByPoint(point));
        Assert.assertEquals(mesh.getPoints().get(1), meshMirrored.getPoints().get(1).mirrorByPoint(point));
        Assert.assertEquals(mesh.getPoints().get(2), meshMirrored.getPoints().get(2).mirrorByPoint(point));
        Assert.assertEquals(mesh.getPoints().get(3), meshMirrored.getPoints().get(3).mirrorByPoint(point));

        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(0).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(2).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(1).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(1).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(2).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(0).getPointIndex());

        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(0).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(2).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(1).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(1).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(2).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(0).getPointIndex());
    }

    @Test
    public void test_mirrorByPlane() {
        Mesh mesh = createMesh();
        Plane plane = new Plane(new Vec3d(1.0, 1.0, 1.0), new Vec3d(0.0, 1.0, 0.0));
        Mesh meshMirrored = mesh.mirrorByPlane(plane);

        Assert.assertEquals(mesh.getPoints().get(0), meshMirrored.getPoints().get(0).mirrorByPlane(plane));
        Assert.assertEquals(mesh.getPoints().get(1), meshMirrored.getPoints().get(1).mirrorByPlane(plane));
        Assert.assertEquals(mesh.getPoints().get(2), meshMirrored.getPoints().get(2).mirrorByPlane(plane));
        Assert.assertEquals(mesh.getPoints().get(3), meshMirrored.getPoints().get(3).mirrorByPlane(plane));

        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(0).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(2).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(1).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(1).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(0).getVertices().get(2).getPointIndex(), meshMirrored.getFaces().get(0).getVertices().get(0).getPointIndex());

        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(0).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(2).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(1).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(1).getPointIndex());
        Assert.assertEquals(mesh.getFaces().get(1).getVertices().get(2).getPointIndex(), meshMirrored.getFaces().get(1).getVertices().get(0).getPointIndex());
    }

    @Test
    public void test_triangulateByEarClipping() {
        Vec3d v4 = new Vec3d(-0.5, 0.0, 0.5);
        final Vertex vertex0 = new Vertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d( 0.0, 0.0));
        final Vertex vertex1 = new Vertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d( 1.0, 0.0));
        final Vertex vertex2 = new Vertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d( 1.0, 1.0));
        final Vertex vertex3 = new Vertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d( 0.0, 1.0));
        final Vertex vertex4 = new Vertex(4, new Vec3d(0.0, 1.0, 0.0), new Vec2d(-0.5, 0.5));
        final Face face = new Face(Arrays.asList(vertex0, vertex1, vertex2, vertex3, vertex4));
        final Mesh mesh = new Mesh(Arrays.asList(v0, v1, v2, v3, v4), Arrays.asList(face));

        final Mesh triangulatedMesh = mesh.triangulateByEarClipping();

        Assert.assertEquals(mesh.getPoints().size(), triangulatedMesh.getPoints().size());
        Assert.assertEquals(mesh.getPoints().get(0), triangulatedMesh.getPoints().get(0));
        Assert.assertEquals(mesh.getPoints().get(1), triangulatedMesh.getPoints().get(1));
        Assert.assertEquals(mesh.getPoints().get(2), triangulatedMesh.getPoints().get(2));
        Assert.assertEquals(mesh.getPoints().get(3), triangulatedMesh.getPoints().get(3));
        Assert.assertEquals(mesh.getPoints().get(4), triangulatedMesh.getPoints().get(4));

        Assert.assertEquals(3, triangulatedMesh.getFaces().size());

        final Face face0 = triangulatedMesh.getFaces().get(0);
        Assert.assertEquals(3, face0.getVertices().size());
        Assert.assertEquals(0, face0.getVertices().get(0).getPointIndex());
        Assert.assertEquals(1, face0.getVertices().get(1).getPointIndex());
        Assert.assertEquals(2, face0.getVertices().get(2).getPointIndex());

        final Face face1 = triangulatedMesh.getFaces().get(1);
        Assert.assertEquals(3, face1.getVertices().size());
        Assert.assertEquals(0, face1.getVertices().get(0).getPointIndex());
        Assert.assertEquals(2, face1.getVertices().get(1).getPointIndex());
        Assert.assertEquals(3, face1.getVertices().get(2).getPointIndex());

        final Face face2 = triangulatedMesh.getFaces().get(2);
        Assert.assertEquals(3, face2.getVertices().size());
        Assert.assertEquals(0, face2.getVertices().get(0).getPointIndex());
        Assert.assertEquals(3, face2.getVertices().get(1).getPointIndex());
        Assert.assertEquals(4, face2.getVertices().get(2).getPointIndex());
    }

    private static Mesh createMesh() {
        final Vertex vertex0 = new Vertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 0.0));
        final Vertex vertex1 = new Vertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 0.0));
        final Vertex vertex2 = new Vertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 1.0));
        final Vertex vertex3 = new Vertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 1.0));
        final Face face0 = new Face(Arrays.asList(vertex2, vertex1, vertex0)); // CW-winding
        final Face face1 = new Face(Arrays.asList(vertex0, vertex3, vertex2)); // CW-winding
        final Mesh mesh = new Mesh(Arrays.asList(v0, v1, v2, v3), Arrays.asList(face0, face1));
        return mesh;
    }
}
