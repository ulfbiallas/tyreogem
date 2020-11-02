package de.ulfbiallas.tyreogem.core.planar;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class CircleTest {

    private static final Line line = Line.createLineWithPointAndDirection(new Vec2d(0, 0), new Vec2d(1, 0));
    private static final LineSegment lineSegment = new LineSegment(new Vec2d(0, 0), new Vec2d(10, 0));
    private static final Ray ray = new Ray(new Vec2d(-1, 0), new Vec2d(1, 0));

    @Test
    public void test_constructor() {
        final Circle circle = new Circle(new Vec2d(3, 2), 4);
        Assert.assertEquals(3.0, circle.getCenter().x, 0.00001);
        Assert.assertEquals(2.0, circle.getCenter().y, 0.00001);
        Assert.assertEquals(4.0, circle.getRadius(), 0.00001);
        Assert.assertEquals(8.0, circle.getDiameter(), 0.00001);
        Assert.assertEquals(8.0*Math.PI, circle.getCircumference(), 0.00001);
    }

    @Test
    public void test_isPointInside() {
        final Circle circle = new Circle(new Vec2d(3, 2), 4);

        Assert.assertTrue(circle.isPointInside(new Vec2d(3, 2)));
        Assert.assertTrue(circle.isPointInside(new Vec2d(-1, 2)));
        Assert.assertTrue(circle.isPointInside(new Vec2d(7, 2)));
        Assert.assertTrue(circle.isPointInside(new Vec2d(3, -2)));
        Assert.assertTrue(circle.isPointInside(new Vec2d(3, 6)));

        Assert.assertFalse(circle.isPointInside(new Vec2d(-2, 2)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(8, 2)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(3, -3)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(3, 7)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(-1, -2)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(-1, 6)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(7, -2)));
        Assert.assertFalse(circle.isPointInside(new Vec2d(7, 6)));
    }

    @Test
    public void test_intersectionWithLine_twoIntersections() {
        final Circle circle = new Circle(new Vec2d(5, 0), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(line);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        Intersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);

        Intersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_oneIntersection() {
        final Circle circle = new Circle(new Vec2d(5, 2), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(line);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_zeroIntersections() {
        final Circle circle = new Circle(new Vec2d(5, 4), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(line);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithLineSegment_twoIntersections() {
        final Circle circle = new Circle(new Vec2d(5, 0), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        Intersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);

        Intersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_oneIntersection() {
        final Circle circle = new Circle(new Vec2d(5, 2), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_oneIntersection2() {
        final Circle circle = new Circle(new Vec2d(0, 0), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(2.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_zeroIntersections() {
        final Circle circle = new Circle(new Vec2d(-3, 0), 2);
        MultiIntersection<Intersection> intersections = circle.intersect(lineSegment);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithRay_twoIntersections() {
        final Circle circle = new Circle(new Vec2d(5, 0), 2);
        MultiIntersection<RayIntersection> intersections = circle.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        RayIntersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(4.0, i1.getParameter(), 0.00001);
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);

        RayIntersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(8.0, i2.getParameter(), 0.00001);
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_oneIntersection() {
        final Circle circle = new Circle(new Vec2d(5, 2), 2);
        MultiIntersection<RayIntersection> intersections = circle.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        RayIntersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(6.0, i.getParameter(), 0.00001);
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_oneIntersection2() {
        final Circle circle = new Circle(new Vec2d(0, 0), 2);
        MultiIntersection<RayIntersection> intersections = circle.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        RayIntersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(3.0, i.getParameter(), 0.00001);
        Assert.assertEquals(2.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_zeroIntersections() {
        final Circle circle = new Circle(new Vec2d(5, 4), 2);
        MultiIntersection<RayIntersection> intersections = circle.intersect(ray);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithRay_zeroIntersections2() {
        final Circle circle = new Circle(new Vec2d(-4, 0), 2);
        MultiIntersection<RayIntersection> intersections = circle.intersect(ray);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }
}
