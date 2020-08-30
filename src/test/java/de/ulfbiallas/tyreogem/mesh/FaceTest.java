package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class FaceTest {

    @Test
    public void test_constructor1() {
        Vertex vertex1 = new Vertex(0, new Vec3d(1.0, 0.0, 0.0), new Vec2d(0.0, 0.0));
        Vertex vertex2 = new Vertex(1, new Vec3d(0.0, 1.0, 1.0), new Vec2d(1.0, 0.0));
        Material material = new Material();
        material.setName("test-material");

        Face face = new Face(Arrays.asList(vertex1, vertex2), material);
        Assert.assertEquals(2, face.getVertices().size());
        Assert.assertEquals(vertex1, face.getVertices().get(0));
        Assert.assertEquals(vertex2, face.getVertices().get(1));
        Assert.assertEquals("test-material", face.getMaterial().getName());
    }

    @Test
    public void test_constructor2() {
        Vertex vertex1 = new Vertex(0, new Vec3d(1.0, 0.0, 0.0), new Vec2d(0.0, 0.0));
        Vertex vertex2 = new Vertex(1, new Vec3d(0.0, 1.0, 1.0), new Vec2d(1.0, 0.0));

        Face face = new Face(Arrays.asList(vertex1, vertex2));
        Assert.assertEquals(2, face.getVertices().size());
        Assert.assertEquals(vertex1, face.getVertices().get(0));
        Assert.assertEquals(vertex2, face.getVertices().get(1));
        Assert.assertNull(face.getMaterial());
    }

    @Test
    public void test_reverseWinding() {
        Vertex vertex0 = new Vertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 0.0));
        Vertex vertex1 = new Vertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 0.0));
        Vertex vertex2 = new Vertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 1.0));
        Vertex vertex3 = new Vertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 1.0));

        Face face = new Face(Arrays.asList(vertex0, vertex1, vertex2, vertex3));
        Face faceWithReversedWinding = face.reverseWinding();
        Assert.assertEquals(vertex3, faceWithReversedWinding.getVertices().get(0));
        Assert.assertEquals(vertex2, faceWithReversedWinding.getVertices().get(1));
        Assert.assertEquals(vertex1, faceWithReversedWinding.getVertices().get(2));
        Assert.assertEquals(vertex0, faceWithReversedWinding.getVertices().get(3));
    }
}
