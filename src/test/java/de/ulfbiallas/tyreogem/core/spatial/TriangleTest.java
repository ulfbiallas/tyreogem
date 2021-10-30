package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

import org.junit.Assert;
import org.junit.Test;

public class TriangleTest {

    @Test
    public void test_constructor() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 6, -2));
        Assert.assertEquals(5.0, triangle.getA().x, 0.00001);
        Assert.assertEquals(3.0, triangle.getA().y, 0.00001);
        Assert.assertEquals(-2.0, triangle.getA().z, 0.00001);
        Assert.assertEquals(8.0, triangle.getB().x, 0.00001);
        Assert.assertEquals(3.0, triangle.getB().y, 0.00001);
        Assert.assertEquals(-2.0, triangle.getB().z, 0.00001);
        Assert.assertEquals(5.0, triangle.getC().x, 0.00001);
        Assert.assertEquals(6.0, triangle.getC().y, 0.00001);
        Assert.assertEquals(-2.0, triangle.getC().z, 0.00001);
        final Plane plane = triangle.getPlane();
        Assert.assertEquals(5.0, plane.getPointOnPlane().x, 0.00001);
        Assert.assertEquals(3.0, plane.getPointOnPlane().y, 0.00001);
        Assert.assertEquals(-2.0, plane.getPointOnPlane().z, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().x, 0.00001);
        Assert.assertEquals(0.0, plane.getNormal().y, 0.00001);
        Assert.assertEquals(1.0, plane.getNormal().z, 0.00001);
    }

    @Test
    public void test_getAB() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 6, -2));
        final LineSegment ab = triangle.getAB();
        Assert.assertEquals(5.0, ab.getStart().x, 0.00001);
        Assert.assertEquals(3.0, ab.getStart().y, 0.00001);
        Assert.assertEquals(-2.0, ab.getStart().z, 0.00001);
        Assert.assertEquals(8.0, ab.getEnd().x, 0.00001);
        Assert.assertEquals(3.0, ab.getEnd().y, 0.00001);
        Assert.assertEquals(-2.0, ab.getEnd().z, 0.00001);
    }

    @Test
    public void test_getBC() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 6, -2));
        final LineSegment bc = triangle.getBC();
        Assert.assertEquals(8.0, bc.getStart().x, 0.00001);
        Assert.assertEquals(3.0, bc.getStart().y, 0.00001);
        Assert.assertEquals(-2.0, bc.getStart().z, 0.00001);
        Assert.assertEquals(5.0, bc.getEnd().x, 0.00001);
        Assert.assertEquals(6.0, bc.getEnd().y, 0.00001);
        Assert.assertEquals(-2.0, bc.getEnd().z, 0.00001);
    }

    @Test
    public void test_getCA() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 6, -2));
        final LineSegment ca = triangle.getCA();
        Assert.assertEquals(5.0, ca.getStart().x, 0.00001);
        Assert.assertEquals(6.0, ca.getStart().y, 0.00001);
        Assert.assertEquals(-2.0, ca.getStart().z, 0.00001);
        Assert.assertEquals(5.0, ca.getEnd().x, 0.00001);
        Assert.assertEquals(3.0, ca.getEnd().y, 0.00001);
        Assert.assertEquals(-2.0, ca.getEnd().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final Line line = Line.createLineThroughTwoPoints(new Vec3d(6, 4, -5), new Vec3d(6, 4, 4));
        final Intersection intersection = triangle.intersect(line);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(6.0, intersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, intersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final Line line = Line.createLineThroughTwoPoints(new Vec3d(-6, 4, -5), new Vec3d(-6, 4, 4));
        final Intersection intersection = triangle.intersect(line);
        Assert.assertFalse(intersection.isIntersecting());
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final LineSegment lineSegment = new LineSegment(new Vec3d(6, 4, -5), new Vec3d(6, 4, 4));
        final Intersection intersection = triangle.intersect(lineSegment);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(6.0, intersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, intersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final LineSegment lineSegment = new LineSegment(new Vec3d(6, 4, 1), new Vec3d(6, 4, 4));
        final Intersection intersection = triangle.intersect(lineSegment);
        Assert.assertFalse(intersection.isIntersecting());
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final Ray ray = new Ray(new Vec3d(6, 4, -5), new Vec3d(0, 0, 1));
        final RayIntersection intersection = triangle.intersect(ray);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(3.0, intersection.getParameter(), 0.00001);
        Assert.assertEquals(6.0, intersection.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection.getIntersection().y, 0.00001);
        Assert.assertEquals(-2.0, intersection.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec3d(5, 3, -2), new Vec3d(8, 3, -2), new Vec3d(5, 10, -2));
        final Ray ray = new Ray(new Vec3d(6, 4, -5), new Vec3d(0, 0, -1));
        final RayIntersection intersection = triangle.intersect(ray);
        Assert.assertFalse(intersection.isIntersecting());
    }
}
