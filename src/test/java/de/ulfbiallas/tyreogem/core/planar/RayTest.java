package de.ulfbiallas.tyreogem.core.planar;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class RayTest {

    private static final Ray ray1 = new Ray(new Vec2d(2,1), new Vec2d(1, 2));
    private static final Ray ray2 = new Ray(new Vec2d(-3,3), new Vec2d(2, -1));
    private static final Ray ray3 = new Ray(new Vec2d(-2,5), new Vec2d(1, 0));
    private static final LineSegment lineSegment = new LineSegment(new Vec2d(3,-1), new Vec2d(3, 4));
    private static final Line line1 = Line.createLineThroughTwoPoints(new Vec2d(3,-1), new Vec2d(3, 4));
    private static final Line line2 = Line.createLineThroughTwoPoints(new Vec2d(-4, 2), new Vec2d(1, 2));

    @Test
    public void test_constructor() {
        final Ray ray = new Ray(new Vec2d(-3,3), new Vec2d(2, -1));
        Assert.assertEquals(-3.0, ray.getOrigin().x, 0.00001);
        Assert.assertEquals(3.0, ray.getOrigin().y, 0.00001);
        Assert.assertEquals(2.0, ray.getDirection().x, 0.00001);
        Assert.assertEquals(-1.0, ray.getDirection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final RayIntersection iRay1ray2 = ray1.intersect(ray2);
        Assert.assertFalse(iRay1ray2.isIntersecting());
        Assert.assertNull(iRay1ray2.getIntersection());
        Assert.assertEquals(iRay1ray2.getParameter(), 0.0, 0.00001);

        final RayIntersection iRay2ray1 = ray2.intersect(ray1);
        Assert.assertFalse(iRay2ray1.isIntersecting());
        Assert.assertNull(iRay2ray1.getIntersection());
        Assert.assertEquals(iRay2ray1.getParameter(), 0.0, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting2() {
        final RayIntersection iRay2ray3 = ray2.intersect(ray3);
        Assert.assertFalse(iRay2ray3.isIntersecting());
        Assert.assertNull(iRay2ray3.getIntersection());
        Assert.assertEquals(iRay2ray3.getParameter(), 0.0, 0.00001);

        final RayIntersection iRay3ray2 = ray3.intersect(ray2);
        Assert.assertFalse(iRay3ray2.isIntersecting());
        Assert.assertNull(iRay3ray2.getIntersection());
        Assert.assertEquals(iRay3ray2.getParameter(), 0.0, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final RayIntersection iRay1ray3 = ray1.intersect(ray3);
        Assert.assertTrue(iRay1ray3.isIntersecting());
        Assert.assertEquals(4.0, iRay1ray3.getIntersection().x, 0.00001);
        Assert.assertEquals(5.0, iRay1ray3.getIntersection().y, 0.00001);
        Assert.assertEquals(iRay1ray3.getParameter(), 2.0, 0.00001);

        final RayIntersection iRay3ray1 = ray3.intersect(ray1);
        Assert.assertTrue(iRay3ray1.isIntersecting());
        Assert.assertEquals(4.0, iRay3ray1.getIntersection().x, 0.00001);
        Assert.assertEquals(5.0, iRay3ray1.getIntersection().y, 0.00001);
        Assert.assertEquals(iRay3ray1.getParameter(), 6.0, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final RayIntersection i = ray1.intersect(lineSegment);
        Assert.assertEquals(3.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(3.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(i.getParameter(), 1.0, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final RayIntersection i = ray3.intersect(lineSegment);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
        Assert.assertEquals(i.getParameter(), 0.0, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        RayIntersection i = ray1.intersect(line1);
        Assert.assertEquals(3.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(3.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(i.getParameter(), 1.0, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_intersecting2() {
        final RayIntersection i = ray3.intersect(line1);
        Assert.assertEquals(3.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(5.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(i.getParameter(), 5.0, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final RayIntersection i = ray3.intersect(line2);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
        Assert.assertEquals(i.getParameter(), 0.0, 0.00001);
    }
}
