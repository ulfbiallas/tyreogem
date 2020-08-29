package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class FaceTest {

    @Test
    public void test_constructor1() {
        FaceVertex faceVertex1 = new FaceVertex(0, new Vec3d(1.0, 0.0, 0.0), new Vec2d(0.0, 0.0));
        FaceVertex faceVertex2 = new FaceVertex(1, new Vec3d(0.0, 1.0, 1.0), new Vec2d(1.0, 0.0));
        Material material = new Material();
        material.setName("test-material");

        Face face = new Face(Arrays.asList(faceVertex1, faceVertex2), material);
        Assert.assertEquals(2, face.getVertices().size());
        Assert.assertEquals(faceVertex1, face.getVertices().get(0));
        Assert.assertEquals(faceVertex2, face.getVertices().get(1));
        Assert.assertEquals("test-material", face.getMaterial().getName());
    }

    @Test
    public void test_constructor2() {
        FaceVertex faceVertex1 = new FaceVertex(0, new Vec3d(1.0, 0.0, 0.0), new Vec2d(0.0, 0.0));
        FaceVertex faceVertex2 = new FaceVertex(1, new Vec3d(0.0, 1.0, 1.0), new Vec2d(1.0, 0.0));

        Face face = new Face(Arrays.asList(faceVertex1, faceVertex2));
        Assert.assertEquals(2, face.getVertices().size());
        Assert.assertEquals(faceVertex1, face.getVertices().get(0));
        Assert.assertEquals(faceVertex2, face.getVertices().get(1));
        Assert.assertNull(face.getMaterial());
    }
}
