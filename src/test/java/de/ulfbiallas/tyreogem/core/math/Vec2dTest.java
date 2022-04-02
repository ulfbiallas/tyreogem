package de.ulfbiallas.tyreogem.core.math;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class Vec2dTest {

    @Test
    public void testConstructor() {
        Vec2d v = new Vec2d(1.0, 2.0);
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(2.0, v.y, 0.00001);
    }

    @Test
    public void testConstructor2() {
        Vec2d v1 = new Vec2d(1.0, 2.0);
        Vec2d v = new Vec2d(v1);
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(2.0, v.y, 0.00001);
    }

    @Test
    public void testConstructorZero() {
        Vec2d v = Vec2d.zero();
        Assert.assertEquals(0.0, v.x, 0.00001);
        Assert.assertEquals(0.0, v.y, 0.00001);
    }

    @Test
    public void testConstructorE1() {
        Vec2d v = Vec2d.e1();
        Assert.assertEquals(1.0, v.x, 0.00001);
        Assert.assertEquals(0.0, v.y, 0.00001);
    }

    @Test
    public void testConstructorE2() {
        Vec2d v = Vec2d.e2();
        Assert.assertEquals(0.0, v.x, 0.00001);
        Assert.assertEquals(1.0, v.y, 0.00001);
    }

    @Test
    public void testClone() {
        Vec2d v = new Vec2d(1.0, 2.0);
        Vec2d vCloned = v.clone();
        Assert.assertEquals(vCloned.x, v.x, 0.00001);
        Assert.assertEquals(vCloned.y, v.y, 0.00001);
        Assert.assertNotEquals(vCloned.hashCode(), v.hashCode());
    }

    @Test
    public void testAdd() {
        Vec2d v1 = new Vec2d(0.0, 2.0);
        Vec2d v2 = new Vec2d(5.0, -3.0);
        Vec2d v = v1.add(v2);
        Assert.assertEquals(5.0, v.x, 0.00001);
        Assert.assertEquals(-1.0, v.y, 0.00001);
    }

    @Test
    public void testSub() {
        Vec2d v1 = new Vec2d(0.0, 2.0);
        Vec2d v2 = new Vec2d(5.0, -3.0);
        Vec2d v = v1.sub(v2);
        Assert.assertEquals(-5.0, v.x, 0.00001);
        Assert.assertEquals(5.0, v.y, 0.00001);
    }

    @Test
    public void testDotProduct() {
        Vec2d v1 = new Vec2d(4.0, 2.0);
        Vec2d v2 = new Vec2d(5.0, -3.0);
        double dotProduct = v1.dot(v2);
        Assert.assertEquals(14.0, dotProduct, 0.00001);
    }

    @Test
    public void testScale() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d vScaled = v.scale(-5.0);
        Assert.assertEquals(15.0, vScaled.x, 0.00001);
        Assert.assertEquals(-10.0, vScaled.y, 0.00001);
    }

    @Test
    public void testScalComponentWise() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d factor = new Vec2d(2.0, 5.0);
        Vec2d vScaled = v.scaleComponentWise(factor);
        Assert.assertEquals(-6.0, vScaled.x, 0.00001);
        Assert.assertEquals(10.0, vScaled.y, 0.00001);
    }

    @Test
    public void testNorm2() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        double vSquaredLength = v.norm2();
        Assert.assertEquals(13.0, vSquaredLength, 0.00001);
    }

    @Test
    public void testNorm() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        double vLength = v.norm();
        Assert.assertEquals(Math.sqrt(13.0), vLength, 0.00001);
    }

    @Test
    public void testNormalize() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d vNormalized = v.normalize();
        Assert.assertEquals(-3.0 / Math.sqrt(13.0), vNormalized.x, 0.00001);
        Assert.assertEquals(2.0 / Math.sqrt(13.0), vNormalized.y, 0.00001);
    }

    @Test
    public void testArrayConversion() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        double[] vArray = v.toArray();
        Assert.assertEquals(-3.0, vArray[0], 0.00001);
        Assert.assertEquals(2.0, vArray[1], 0.00001);
    }

    @Test
    public void testArithmeticMean() {
        List<Vec2d> points = Arrays.asList(
            new Vec2d(0.0, 2.0),
            new Vec2d(5.4, -3.0),
            new Vec2d(5.0, -1.0),
            new Vec2d(-1.6, -0.3),
            new Vec2d(2.4, 5.3)
        );
        Vec2d mean = Vec2d.arithmeticMean(points);
        Assert.assertEquals(2.24, mean.x, 0.00001);
        Assert.assertEquals(0.60, mean.y, 0.00001);
    }

    @Test
    public void testNegate() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d vNegated = v.negate();
        Assert.assertEquals(-v.x, vNegated.x, 0.00001);
        Assert.assertEquals(-v.y, vNegated.y, 0.00001);
    }

    @Test
    public void testPerpendicular() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d vPerpendicular = v.perpendicular();
        double dotProduct = v.dot(vPerpendicular);
        Assert.assertEquals(0, dotProduct, 0.00001);
    }

    @Test
    public void testDistanceToPoint() {
        Vec2d v = new Vec2d(-3.0, 2.0);
        Vec2d point = new Vec2d(4, 1);
        double distance = point.sub(v).norm();
        Assert.assertEquals(v.distanceTo(point), distance, 0.00001);
    }

    @Test
    public void testEquals() {
        Vec2d v1 = new Vec2d(-3.0, 4.0);
        Vec2d v2 = new Vec2d(-3.0, 4.0);
        Assert.assertEquals(v1, v1);
        Assert.assertEquals(v2, v2);
        Assert.assertEquals(v1, v2);

        Vec2d v1b = new Vec2d(-3.0, 4.00001);
        Vec2d v2b = new Vec2d(-3.000001, 4.0);
        Assert.assertEquals(v1b, v1b);
        Assert.assertEquals(v2b, v2b);
        Assert.assertNotEquals(v1b, v2b);
        Assert.assertNotEquals(v1, v1b);
        Assert.assertNotEquals(v2, v2b);
    }
}
