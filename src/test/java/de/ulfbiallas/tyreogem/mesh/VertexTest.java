package de.ulfbiallas.tyreogem.mesh;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class VertexTest {

    @Test
    public void test_constructor() {
        Vertex vertex = new Vertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Assert.assertEquals(3, vertex.getPointIndex());
        Assert.assertEquals(new Vec3d(-2.0, 3.4, 0.5), vertex.getNormal());
        Assert.assertEquals(new Vec2d(0.5, -1.0), vertex.getTextureCoordinates());
    }

    @Test
    public void test_equals() {
        Vertex vertex1 = new Vertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Vertex vertex2 = new Vertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Assert.assertEquals(vertex1, vertex1);
        Assert.assertEquals(vertex2, vertex2);
        Assert.assertEquals(vertex1, vertex2);
        Assert.assertEquals(vertex2, vertex1);

        Vertex vertex1b = new Vertex(4, new Vec3d(-2.5, 3.4, 0.5), new Vec2d(0.5, -1.0));
        Vertex vertex2b = new Vertex(3, new Vec3d(-2.0, 3.4, 0.5), new Vec2d(0.5, -2.0));
        Assert.assertNotEquals(vertex1, vertex1b);
        Assert.assertNotEquals(vertex2, vertex2b);
        Assert.assertNotEquals(vertex1b, vertex2b);

        Vertex vertex1c = new Vertex(4, null, new Vec2d(0.5, -1.0));
        Vertex vertex2c = new Vertex(3, new Vec3d(-2.0, 3.4, 0.5), null);
        Vertex vertex3c = new Vertex(5, null,null);
        Assert.assertEquals(vertex1c, vertex1c);
        Assert.assertEquals(vertex2c, vertex2c);
        Assert.assertEquals(vertex3c, vertex3c);
        Assert.assertNotEquals(vertex1c, vertex2c);
        Assert.assertNotEquals(vertex1c, vertex3c);
        Assert.assertNotEquals(vertex2c, vertex1c);
        Assert.assertNotEquals(vertex2c, vertex3c);
        Assert.assertNotEquals(vertex1, vertex1c);
        Assert.assertNotEquals(vertex2, vertex2c);
    }
}
