package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

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

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(0.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(1.0, 1.0), mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());
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

        Assert.assertEquals(2, meshWithReversedWinding.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(1, meshWithReversedWinding.getFaces().get(0).getVertices().get(1).getPointIndex());
        Assert.assertEquals(0, meshWithReversedWinding.getFaces().get(0).getVertices().get(2).getPointIndex());

        Assert.assertEquals(0, meshWithReversedWinding.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(3, meshWithReversedWinding.getFaces().get(1).getVertices().get(1).getPointIndex());
        Assert.assertEquals(2, meshWithReversedWinding.getFaces().get(1).getVertices().get(2).getPointIndex());
    }

    private static Mesh createMesh() {
        final Vertex vertex0 = new Vertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 0.0));
        final Vertex vertex1 = new Vertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 0.0));
        final Vertex vertex2 = new Vertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 1.0));
        final Vertex vertex3 = new Vertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 1.0));
        final Face face0 = new Face(Arrays.asList(vertex0, vertex1, vertex2));
        final Face face1 = new Face(Arrays.asList(vertex2, vertex3, vertex0));
        final Mesh mesh = new Mesh(Arrays.asList(v0, v1, v2, v3), Arrays.asList(face0, face1));
        return mesh;
    }
}
