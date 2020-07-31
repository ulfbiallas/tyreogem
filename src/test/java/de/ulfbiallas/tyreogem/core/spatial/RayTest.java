package de.ulfbiallas.tyreogem.core.spatial;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class RayTest {

    private static final Ray ray = new Ray(new Vec3d(3, 2, -1), new Vec3d(0, 1, 0));

    @Test
    public void test_constructor() {
        final Ray ray = new Ray(new Vec3d(-3,2, 5), new Vec3d(2, -1, 0));
        Assert.assertEquals(-3.0, ray.getOrigin().x, 0.00001);
        Assert.assertEquals(2.0, ray.getOrigin().y, 0.00001);
        Assert.assertEquals(5.0, ray.getOrigin().z, 0.00001);
        Assert.assertEquals(2.0, ray.getDirection().x, 0.00001);
        Assert.assertEquals(-1.0, ray.getDirection().y, 0.00001);
        Assert.assertEquals(0.0, ray.getDirection().z, 0.00001);
    }

    @Test
    public void test_distanceTo() {
        final double distance1 = ray.distanceTo(new Vec3d(3, 4, -1));
        Assert.assertEquals(0.0, distance1, 0.00001);

        final double distance2 = ray.distanceTo(new Vec3d(0, 5, -1));
        Assert.assertEquals(3.0, distance2, 0.00001);

        final double distance3 = ray.distanceTo(new Vec3d(3, 9, 1));
        Assert.assertEquals(2.0, distance3, 0.00001);

        final double distance4 = ray.distanceTo(new Vec3d(3, -5, -1));
        Assert.assertEquals(7.0, distance4, 0.00001);
    }

}
