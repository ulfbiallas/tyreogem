package de.ulfbiallas.tyreogem.mesh;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class FaceVertexTest {

    @Test
    public void test_constructor() {
        FaceVertex faceVertex = new FaceVertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Assert.assertEquals(3, faceVertex.getVertexIndex());
        Assert.assertEquals(new Vec3d(-2.0, 3.4, 0.5), faceVertex.getNormal());
        Assert.assertEquals(new Vec2d(0.5, -1.0), faceVertex.getTextureCoordinates());
    }

    @Test
    public void test_equals() {
        FaceVertex faceVertex1 = new FaceVertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        FaceVertex faceVertex2 = new FaceVertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Assert.assertEquals(faceVertex1, faceVertex1);
        Assert.assertEquals(faceVertex2, faceVertex2);
        Assert.assertEquals(faceVertex1, faceVertex2);
        Assert.assertEquals(faceVertex2, faceVertex1);

        FaceVertex faceVertex1b = new FaceVertex(4, new Vec3d(-2.5, 3.4, 0.5), new Vec2d(0.5, -1.0));
        FaceVertex faceVertex2b = new FaceVertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -2.0));
        Assert.assertNotEquals(faceVertex1, faceVertex1b);
        Assert.assertNotEquals(faceVertex2, faceVertex2b);
        Assert.assertNotEquals(faceVertex1b, faceVertex2b);

        FaceVertex faceVertex1c = new FaceVertex(4, null, new Vec2d(0.5, -1.0));
        FaceVertex faceVertex2c = new FaceVertex(3, new Vec3d(-2.0, 3.4, 0.5), null);
        FaceVertex faceVertex3c = new FaceVertex(5, null,null);
        Assert.assertEquals(faceVertex1c, faceVertex1c);
        Assert.assertEquals(faceVertex2c, faceVertex2c);
        Assert.assertEquals(faceVertex3c, faceVertex3c);
        Assert.assertNotEquals(faceVertex1c, faceVertex2c);
        Assert.assertNotEquals(faceVertex1c, faceVertex3c);
        Assert.assertNotEquals(faceVertex2c, faceVertex1c);
        Assert.assertNotEquals(faceVertex2c, faceVertex3c);
        Assert.assertNotEquals(faceVertex1, faceVertex1c);
        Assert.assertNotEquals(faceVertex2, faceVertex2c);
    }
}
