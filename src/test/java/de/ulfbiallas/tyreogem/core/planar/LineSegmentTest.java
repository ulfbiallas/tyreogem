package de.ulfbiallas.tyreogem.core.planar;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class LineSegmentTest {

    private static final LineSegment lineSegment1 = new LineSegment(new Vec2d(4, 1), new Vec2d(-2, 4));
    private static final LineSegment lineSegment2 = new LineSegment(new Vec2d(0, 5), new Vec2d(8, 7));
    private static final LineSegment lineSegment3 = new LineSegment(new Vec2d(4, 2), new Vec2d(4, 7));
    private static final LineSegment lineSegment4 = new LineSegment(new Vec2d(5, 6), new Vec2d(3, 2));
    private static final Ray ray = new Ray(new Vec2d(2, 3), new Vec2d(1, 0));
    private static final Line line = Line.createLineThroughTwoPoints(new Vec2d(-1,1), new Vec2d(1, 5));

    @Test
    public void test_constructor() {
        final LineSegment lineSegment = new LineSegment(new Vec2d(5, 6), new Vec2d(3, 2));
        Assert.assertEquals(5.0, lineSegment.getStart().x, 0.00001);
        Assert.assertEquals(6.0, lineSegment.getStart().y, 0.00001);
        Assert.assertEquals(3.0, lineSegment.getEnd().x, 0.00001);
        Assert.assertEquals(2.0, lineSegment.getEnd().y, 0.00001);
    }

    @Test
    public void test_getDirection() {
        final LineSegment lineSegment = new LineSegment(new Vec2d(4, 2), new Vec2d(8, 2));
        Assert.assertEquals(1.0, lineSegment.getDirection().x, 0.00001);
        Assert.assertEquals(0.0, lineSegment.getDirection().y, 0.00001);
    }

    @Test
    public void test_getLength() {
        final LineSegment lineSegment = new LineSegment(new Vec2d(4, 2), new Vec2d(8, 2));
        Assert.assertEquals(4.0, lineSegment.getLength(), 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final Intersection iSegment2Segment3 = lineSegment2.intersect(lineSegment3);
        Assert.assertTrue(iSegment2Segment3.isIntersecting());
        Assert.assertEquals(4.0, iSegment2Segment3.getIntersection().x, 0.00001);
        Assert.assertEquals(6.0, iSegment2Segment3.getIntersection().y, 0.00001);

        final Intersection iSegment3Segment2 = lineSegment3.intersect(lineSegment2);
        Assert.assertTrue(iSegment3Segment2.isIntersecting());
        Assert.assertEquals(4.0, iSegment3Segment2.getIntersection().x, 0.00001);
        Assert.assertEquals(6.0, iSegment3Segment2.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting2() {
        final Intersection iSegment3Segment4 = lineSegment3.intersect(lineSegment4);
        Assert.assertTrue(iSegment3Segment4.isIntersecting());
        Assert.assertEquals(4.0, iSegment3Segment4.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, iSegment3Segment4.getIntersection().y, 0.00001);

        final Intersection iSegment4Segment3 = lineSegment4.intersect(lineSegment3);
        Assert.assertTrue(iSegment4Segment3.isIntersecting());
        Assert.assertEquals(4.0, iSegment4Segment3.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, iSegment4Segment3.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final Intersection iSegment1Segment2 = lineSegment1.intersect(lineSegment2);
        Assert.assertFalse(iSegment1Segment2.isIntersecting());
        Assert.assertNull(iSegment1Segment2.getIntersection());

        final Intersection iSegment2Segment1 = lineSegment2.intersect(lineSegment1);
        Assert.assertFalse(iSegment2Segment1.isIntersecting());
        Assert.assertNull(iSegment2Segment1.getIntersection());
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting2() {
        final Intersection iSegment2Segment4 = lineSegment2.intersect(lineSegment4);
        Assert.assertFalse(iSegment2Segment4.isIntersecting());
        Assert.assertNull(iSegment2Segment4.getIntersection());

        final Intersection iSegment4Segment2 = lineSegment4.intersect(lineSegment2);
        Assert.assertFalse(iSegment4Segment2.isIntersecting());
        Assert.assertNull(iSegment4Segment2.getIntersection());
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting3() {
        final Intersection iSegment1Segment4 = lineSegment1.intersect(lineSegment4);
        Assert.assertFalse(iSegment1Segment4.isIntersecting());
        Assert.assertNull(iSegment1Segment4.getIntersection());

        final Intersection iSegment4Segment1 = lineSegment4.intersect(lineSegment1);
        Assert.assertFalse(iSegment4Segment1.isIntersecting());
        Assert.assertNull(iSegment4Segment1.getIntersection());
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final Intersection i = lineSegment3.intersect(ray);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(4.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(3.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final Intersection i = lineSegment1.intersect(ray);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        final Intersection i = lineSegment1.intersect(line);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(0.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(3.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final Intersection i = lineSegment3.intersect(line);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
    }

    @Test
    public void test_intersectionWithCircle_intersecting() {
        final Circle circle = new Circle(new Vec2d(4, 1), 3);
        final MultiIntersection<Intersection> intersections = lineSegment4.intersect(circle);
        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(4.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_distanceTo() {
        final double distance1 = lineSegment3.distanceTo(new Vec2d(4, 4));
        Assert.assertEquals(0.0, distance1, 0.00001);

        final double distance2 = lineSegment3.distanceTo(new Vec2d(3, 5));
        Assert.assertEquals(1.0, distance2, 0.00001);

        final double distance3 = lineSegment3.distanceTo(new Vec2d(4, 0));
        Assert.assertEquals(2.0, distance3, 0.00001);

        final double distance4 = lineSegment3.distanceTo(new Vec2d(4, 9));
        Assert.assertEquals(2.0, distance4, 0.00001);
    }
}
