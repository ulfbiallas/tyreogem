package de.ulfbiallas.tyreogem.core.spatial;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class PlaneTest {

    @Test
    public void test_constructor_pointAndDirection() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        Assert.assertEquals(5.0, plane.getPointOnPlane().x, 0.00001);
        Assert.assertEquals(3.0, plane.getPointOnPlane().y, 0.00001);
        Assert.assertEquals(-2.0, plane.getPointOnPlane().z, 0.00001);
        Assert.assertEquals(1.0, plane.getNormal().x, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().y, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().z, 0.00001);
    }

    @Test
    public void test_constructor_threePoints() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 6, -2));
        Assert.assertEquals(5.0, plane.getPointOnPlane().x, 0.00001);
        Assert.assertEquals(3.0, plane.getPointOnPlane().y, 0.00001);
        Assert.assertEquals(-2.0, plane.getPointOnPlane().z, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().x, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().y, 0.00001);
        Assert.assertEquals(1.0, plane.getNormal().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final Line line = Line.createLineThroughTwoPoints(new Vec3d(0, 4, -2), new Vec3d(10, 4, -2));
        final Intersection intersection = plane.intersect(line);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(5.0, intersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, intersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final Line line = Line.createLineThroughTwoPoints(new Vec3d(0, 4, -2), new Vec3d(0, 5, -3));
        final Intersection intersection = plane.intersect(line);
        Assert.assertFalse(intersection.isIntersecting());
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final LineSegment lineSegment = new LineSegment(new Vec3d(0, 4, -2), new Vec3d(10, 4, -2));
        final Intersection intersection = plane.intersect(lineSegment);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(5.0, intersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, intersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final LineSegment lineSegment = new LineSegment(new Vec3d(8, 4, -2), new Vec3d(10, 4, -2));
        final Intersection intersection = plane.intersect(lineSegment);
        Assert.assertFalse(intersection.isIntersecting());
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final Ray ray = new Ray(new Vec3d(0, 4, -2), new Vec3d(1, 0, 0));
        final RayIntersection rayIntersection = plane.intersect(ray);
        Assert.assertTrue(rayIntersection.isIntersecting());
        Assert.assertEquals(5.0, rayIntersection.getParameter(), 0.00001);
        Assert.assertEquals(5.0, rayIntersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, rayIntersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, rayIntersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        final Ray ray = new Ray(new Vec3d(0, 4, -2), new Vec3d(-1, 0, 0));
        final RayIntersection rayIntersection = plane.intersect(ray);
        Assert.assertFalse(rayIntersection.isIntersecting());
    }

    @Test
    public void test_constructor_getSideOfPlane() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        Assert.assertEquals(1.0, plane.getSideOfPlane(new Vec3d(8, 3, -2)), 0.00001);
        Assert.assertEquals(1.0, plane.getSideOfPlane(new Vec3d(6, 7, -1)), 0.00001);
        Assert.assertEquals(0.0, plane.getSideOfPlane(new Vec3d(5, 4, -3)), 0.00001);
        Assert.assertEquals(-1.0, plane.getSideOfPlane(new Vec3d(4, 3, 0)), 0.00001);
        Assert.assertEquals(-1.0, plane.getSideOfPlane(new Vec3d(2, 2, -2)), 0.00001);
    }

    @Test
    public void test_constructor_distanceTo() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        Assert.assertEquals(3.0, plane.distanceTo(new Vec3d(8, 3, -2)), 0.00001);
        Assert.assertEquals(1.0, plane.distanceTo(new Vec3d(6, 7, -1)), 0.00001);
        Assert.assertEquals(0.0, plane.distanceTo(new Vec3d(5, 4, -3)), 0.00001);
        Assert.assertEquals(1.0, plane.distanceTo(new Vec3d(4, 3, 0)), 0.00001);
        Assert.assertEquals(3.0, plane.distanceTo(new Vec3d(2, 2, -2)), 0.00001);
    }

    @Test
    public void test_constructor_signedDistanceTo() {
        final Plane plane = new Plane(new Vec3d(5, 3, -2), new Vec3d(1, 0, 0));
        Assert.assertEquals(3.0, plane.signedDistanceTo(new Vec3d(8, 3, -2)), 0.00001);
        Assert.assertEquals(1.0, plane.signedDistanceTo(new Vec3d(6, 7, -1)), 0.00001);
        Assert.assertEquals(0.0, plane.signedDistanceTo(new Vec3d(5, 4, -3)), 0.00001);
        Assert.assertEquals(-1.0, plane.signedDistanceTo(new Vec3d(4, 3, 0)), 0.00001);
        Assert.assertEquals(-3.0, plane.signedDistanceTo(new Vec3d(2, 2, -2)), 0.00001);
    }
}
