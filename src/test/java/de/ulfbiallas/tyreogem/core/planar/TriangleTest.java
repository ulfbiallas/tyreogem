package de.ulfbiallas.tyreogem.core.planar;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class TriangleTest {

    @Test
    public void test_constructor() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        Assert.assertEquals(5.0, triangle.getA().x, 0.00001);
        Assert.assertEquals(3.0, triangle.getA().y, 0.00001);
        Assert.assertEquals(8.0, triangle.getB().x, 0.00001);
        Assert.assertEquals(3.0, triangle.getB().y, 0.00001);
        Assert.assertEquals(5.0, triangle.getC().x, 0.00001);
        Assert.assertEquals(6.0, triangle.getC().y, 0.00001);
    }

    @Test
    public void test_isPointInside_inside() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Vec2d point = new Vec2d(6, 4);
        Assert.assertTrue(triangle.isPointInside(point));
    }

    @Test
    public void test_isPointInside_onEdge() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Vec2d point = new Vec2d(5, 3);
        Assert.assertTrue(triangle.isPointInside(point));
    }

    @Test
    public void test_isPointInside_outside() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Vec2d point = new Vec2d(-6, 4);
        Assert.assertFalse(triangle.isPointInside(point));
    }

    @Test
    public void test_getAB() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment ab = triangle.getAB();
        Assert.assertEquals(5.0, ab.getStart().x, 0.00001);
        Assert.assertEquals(3.0, ab.getStart().y, 0.00001);
        Assert.assertEquals(8.0, ab.getEnd().x, 0.00001);
        Assert.assertEquals(3.0, ab.getEnd().y, 0.00001);
    }

    @Test
    public void test_getBC() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment bc = triangle.getBC();
        Assert.assertEquals(8.0, bc.getStart().x, 0.00001);
        Assert.assertEquals(3.0, bc.getStart().y, 0.00001);
        Assert.assertEquals(5.0, bc.getEnd().x, 0.00001);
        Assert.assertEquals(6.0, bc.getEnd().y, 0.00001);
    }

    @Test
    public void test_getCA() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment ca = triangle.getCA();
        Assert.assertEquals(5.0, ca.getStart().x, 0.00001);
        Assert.assertEquals(6.0, ca.getStart().y, 0.00001);
        Assert.assertEquals(5.0, ca.getEnd().x, 0.00001);
        Assert.assertEquals(3.0, ca.getEnd().y, 0.00001);
    }

    @Test
    public void test_getEdges() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final List<LineSegment> edges = triangle.getEdges();
        Assert.assertEquals(triangle.getAB(), edges.get(0));
        Assert.assertEquals(triangle.getBC(), edges.get(1));
        Assert.assertEquals(triangle.getCA(), edges.get(2));
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Line line = Line.createLineThroughTwoPoints(new Vec2d(0, 4), new Vec2d(10, 4));
        final MultiIntersection<Intersection> intersection = triangle.intersect(line);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(2, intersection.getNumberOfIntersections());
        final Vec2d intersection0 = intersection.getIntersections().get(0).getIntersection();
        Assert.assertEquals(7.0, intersection0.x, 0.00001);
        Assert.assertEquals(4.0, intersection0.y, 0.00001);
        final Vec2d intersection1 = intersection.getIntersections().get(1).getIntersection();
        Assert.assertEquals(5.0, intersection1.x, 0.00001);
        Assert.assertEquals(4.0, intersection1.y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Line line = Line.createLineThroughTwoPoints(new Vec2d(-2, 2), new Vec2d(2, -2));
        final MultiIntersection<Intersection> intersection = triangle.intersect(line);
        Assert.assertFalse(intersection.isIntersecting());
        Assert.assertEquals(0, intersection.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment lineSegment = new LineSegment(new Vec2d(0, 4), new Vec2d(10, 4));
        final MultiIntersection<Intersection> intersection = triangle.intersect(lineSegment);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(2, intersection.getNumberOfIntersections());
        final Vec2d intersection0 = intersection.getIntersections().get(0).getIntersection();
        Assert.assertEquals(7.0, intersection0.x, 0.00001);
        Assert.assertEquals(4.0, intersection0.y, 0.00001);
        final Vec2d intersection1 = intersection.getIntersections().get(1).getIntersection();
        Assert.assertEquals(5.0, intersection1.x, 0.00001);
        Assert.assertEquals(4.0, intersection1.y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_halfIntersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment lineSegment = new LineSegment(new Vec2d(6, 4), new Vec2d(10, 4));
        final MultiIntersection<Intersection> intersection = triangle.intersect(lineSegment);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(1, intersection.getNumberOfIntersections());
        final Vec2d intersection0 = intersection.getIntersections().get(0).getIntersection();
        Assert.assertEquals(7.0, intersection0.x, 0.00001);
        Assert.assertEquals(4.0, intersection0.y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final LineSegment lineSegment = new LineSegment(new Vec2d(-2, 2), new Vec2d(2, -2));
        final MultiIntersection<Intersection> intersection = triangle.intersect(lineSegment);
        Assert.assertFalse(intersection.isIntersecting());
        Assert.assertEquals(0, intersection.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Ray ray = new Ray(new Vec2d(0, 4), new Vec2d(1, 0));
        final MultiIntersection<RayIntersection> intersection = triangle.intersect(ray);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(2, intersection.getNumberOfIntersections());
        final RayIntersection intersection0 = intersection.getIntersections().get(0);
        Assert.assertEquals(5.0, intersection0.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection0.getIntersection().y, 0.00001);
        Assert.assertEquals(5.0, intersection0.getParameter(), 0.00001);
        final RayIntersection intersection1 = intersection.getIntersections().get(1);
        Assert.assertEquals(7.0, intersection1.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection1.getIntersection().y, 0.00001);
        Assert.assertEquals(7.0, intersection1.getParameter(), 0.00001);
        Assert.assertTrue(intersection0.getParameter() <= intersection1.getParameter());
    }

    @Test
    public void test_intersectionWithRay_halfIntersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Ray ray = new Ray(new Vec2d(6, 4), new Vec2d(1, 0));
        final MultiIntersection<RayIntersection> intersection = triangle.intersect(ray);
        Assert.assertTrue(intersection.isIntersecting());
        Assert.assertEquals(1, intersection.getNumberOfIntersections());
        final RayIntersection intersection0 = intersection.getIntersections().get(0);
        Assert.assertEquals(7.0, intersection0.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, intersection0.getIntersection().y, 0.00001);
        Assert.assertEquals(1.0, intersection0.getParameter(), 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final Triangle triangle = new Triangle(new Vec2d(5, 3), new Vec2d(8, 3), new Vec2d(5, 6));
        final Ray ray = new Ray(new Vec2d(-2, 2), new Vec2d(1, -1));
        final MultiIntersection<RayIntersection> intersection = triangle.intersect(ray);
        Assert.assertFalse(intersection.isIntersecting());
        Assert.assertEquals(0, intersection.getNumberOfIntersections());
    }
}

