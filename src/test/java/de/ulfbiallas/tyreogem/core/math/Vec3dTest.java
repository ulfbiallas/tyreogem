package de.ulfbiallas.tyreogem.core.math;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.spatial.Plane;

public class Vec3dTest {

    @Test
    public void testConstructor() {
        Vec3d v = new Vec3d(1.0, 2.0, -3.0);
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(2.0, v.y, 0.00001);
        Assert.assertEquals(-3.0, v.z, 0.00001);
    }

    @Test
    public void testConstructor2() {
        Vec3d v1 = new Vec3d(1.0, 2.0, -3.0);
        Vec3d v = new Vec3d(v1);
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(2.0, v.y, 0.00001);
        Assert.assertEquals(-3.0, v.z, 0.00001);
    }

    @Test
    public void testConstructorZero() {
        Vec3d v = Vec3d.zero();
        Assert.assertEquals(0.0, v.x, 0.00001);
        Assert.assertEquals(0.0, v.y, 0.00001);
        Assert.assertEquals(0.0, v.z, 0.00001);
    }

    @Test
    public void testConstructorE1() {
        Vec3d v = Vec3d.e1();
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(0.0, v.y, 0.00001);
        Assert.assertEquals(0.0, v.z, 0.00001);
    }

    @Test
    public void testConstructorE2() {
        Vec3d v = Vec3d.e2();
        Assert.assertEquals(0.0, v.x, 0.00001);
        Assert.assertEquals(1.0, v.y, 0.00001);
        Assert.assertEquals(0.0, v.z, 0.00001);
    }

    @Test
    public void testConstructorE3() {
        Vec3d v = Vec3d.e3();
        Assert.assertEquals(0.0, v.x, 0.00001);
        Assert.assertEquals(0.0, v.y, 0.00001);
        Assert.assertEquals(1.0, v.z, 0.00001);
    }

    @Test
    public void testClone() {
        Vec3d v = new Vec3d(1.0, 2.0, -3.0);
        Vec3d vCloned = v.clone();
        Assert.assertEquals(vCloned.x, v.x, 0.00001);
        Assert.assertEquals(vCloned.y, v.y, 0.00001);
        Assert.assertEquals(vCloned.z, v.z, 0.00001);
        Assert.assertNotEquals(vCloned.hashCode(), v.hashCode());
    }

    @Test
    public void testAdd() {
        Vec3d v1 = new Vec3d(0.0, 2.0, -1.0);
        Vec3d v2 = new Vec3d(5.0, -3.0, 8.0);
        Vec3d v = v1.add(v2);
        Assert.assertEquals(5.0, v.x, 0.00001);
        Assert.assertEquals(-1.0, v.y, 0.00001);
        Assert.assertEquals(7.0, v.z, 0.00001);
    }

    @Test
    public void testSub() {
        Vec3d v1 = new Vec3d(0.0, 2.0, -1.0);
        Vec3d v2 = new Vec3d(5.0, -3.0, 8.0);
        Vec3d v = v1.sub(v2);
        Assert.assertEquals(-5.0, v.x, 0.00001);
        Assert.assertEquals(5.0, v.y, 0.00001);
        Assert.assertEquals(-9.0, v.z, 0.00001);
    }

    @Test
    public void testDotProduct() {
        Vec3d v1 = new Vec3d(4.0, 2.0, -1.0);
        Vec3d v2 = new Vec3d(5.0, -3.0, 2.0);
        double dotProduct = v1.dot(v2);
        Assert.assertEquals(12.0, dotProduct, 0.00001);
    }

    @Test
    public void testScale() {
        Vec3d v = new Vec3d(-3.0, 2.0, 5.0);
        Vec3d vScaled = v.scale(-5.0f);
        Assert.assertEquals(15.0, vScaled.x, 0.00001);
        Assert.assertEquals(-10.0, vScaled.y, 0.00001);
        Assert.assertEquals(-25.0, vScaled.z, 0.00001);
    }
    @Test

    public void testNorm2() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        double vSquaredLength = v.norm2();
        Assert.assertEquals(29.0, vSquaredLength, 0.00001);
    }

    @Test
    public void testNorm() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        double vLength = v.norm();
        Assert.assertEquals(Math.sqrt(29.0), vLength, 0.00001);
    }

    @Test
    public void testNormalize() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        Vec3d vNormalized = v.normalize();
        Assert.assertEquals(-3.0 / Math.sqrt(29.0), vNormalized.x, 0.00001);
        Assert.assertEquals(2.0 / Math.sqrt(29.0), vNormalized.y, 0.00001);
        Assert.assertEquals(4.0 / Math.sqrt(29.0), vNormalized.z, 0.00001);
    }

    @Test
    public void testCross() {
        Vec3d v1 = new Vec3d(3.0, 2.0, -1.0);
        Vec3d v2 = new Vec3d(5.0, -3.0, 8.0);
        Vec3d v = v1.cross(v2);
        Assert.assertEquals(13.0, v.x, 0.00001);
        Assert.assertEquals(-29.0, v.y, 0.00001);
        Assert.assertEquals(-19.0, v.z, 0.00001);
    }

    @Test
    public void testArrayConversion() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        double[] vArray = v.toArray();
        Assert.assertEquals(-3.0, vArray[0], 0.00001);
        Assert.assertEquals(2.0, vArray[1], 0.00001);
        Assert.assertEquals(4.0, vArray[2], 0.00001);
    }

    @Test
    public void testArithmeticMean() {
        List<Vec3d> points = Arrays.asList(
            new Vec3d(0.0, 2.0, -1.0),
            new Vec3d(5.4, -3.0, 8.0),
            new Vec3d(5.0, -1.0, 3.2),
            new Vec3d(-1.6, -0.3, 2.2),
            new Vec3d(2.4, 5.3, -1.4)
        );
        Vec3d mean = Vec3d.arithmeticMean(points);
        Assert.assertEquals(2.24, mean.x, 0.00001);
        Assert.assertEquals(0.60, mean.y, 0.00001);
        Assert.assertEquals(2.20, mean.z, 0.00001);
    }

    @Test
    public void testNegate() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        Vec3d vNegated = v.negate();
        Assert.assertEquals(-v.x, vNegated.x, 0.00001);
        Assert.assertEquals(-v.y, vNegated.y, 0.00001);
        Assert.assertEquals(-v.z, vNegated.z, 0.00001);
    }

    @Test
    public void testDistanceToPoint() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        Vec3d point = new Vec3d(4.0, 1.0, -3.0);
        double distance = point.sub(v).norm();
        Assert.assertEquals(v.distanceTo(point), distance, 0.00001);
    }

    @Test
    public void testMirrorByPoint() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        Vec3d point = new Vec3d(1.0, 1.0, 1.0);
        Vec3d vMirrored = v.mirrorByPoint(point);
        Assert.assertEquals(5.0, vMirrored.x, 0.00001);
        Assert.assertEquals(0.0, vMirrored.y, 0.00001);
        Assert.assertEquals(-2.0, vMirrored.z, 0.00001);
    }

    @Test
    public void testMirrorByPlane() {
        Vec3d v = new Vec3d(-3.0, 2.0, 4.0);
        Plane plane = new Plane(new Vec3d(1.0, 1.0, 1.0), new Vec3d(0.0, 1.0, 0.0));
        Vec3d vMirrored = v.mirrorByPlane(plane);
        Assert.assertEquals(-3.0, vMirrored.x, 0.00001);
        Assert.assertEquals(0.0, vMirrored.y, 0.00001);
        Assert.assertEquals(4.0, vMirrored.z, 0.00001);
    }

    @Test
    public void testEquals() {
        Vec3d v1 = new Vec3d(-3.0, 2.0, 4.0);
        Vec3d v2 = new Vec3d(-3.0, 2.0, 4.0);
        Assert.assertEquals(v1, v1);
        Assert.assertEquals(v2, v2);
        Assert.assertEquals(v1, v2);

        Vec3d v1b = new Vec3d(-3.0, 2.0, 4.00001);
        Vec3d v2b = new Vec3d(-3.000001, 2.0, 4.0);
        Assert.assertEquals(v1b, v1b);
        Assert.assertEquals(v2b, v2b);
        Assert.assertNotEquals(v1b, v2b);
        Assert.assertNotEquals(v1, v1b);
        Assert.assertNotEquals(v2, v2b);
    }
}
