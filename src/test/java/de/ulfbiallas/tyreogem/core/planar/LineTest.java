package de.ulfbiallas.tyreogem.core.planar;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class LineTest {

    private static final Line line1 = Line.createLineThroughTwoPoints(new Vec2d(5, 6), new Vec2d(3, 2));
    private static final Line line2 = Line.createLineThroughTwoPoints(new Vec2d(4, 2), new Vec2d(4, 7));
    private static final Line line3 = Line.createLineThroughTwoPoints(new Vec2d(2, 2), new Vec2d(2, 7));
    private static final LineSegment lineSegment = new LineSegment(new Vec2d(1, 2), new Vec2d(3, 1));
    private static final Ray ray = new Ray(new Vec2d(3, 5), new Vec2d(1, 0));

    @Test
    public void testCreateLineWithPointAndDirection() {
        final Line line = Line.createLineWithPointAndDirection(new Vec2d(5, 6), new Vec2d(3, 2));
        Assert.assertEquals(5.0, line.getPointOnLine().x, 0.00001);
        Assert.assertEquals(6.0, line.getPointOnLine().y, 0.00001);
        Assert.assertEquals(3, line.getDirection().x, 0.00001);
        Assert.assertEquals(2, line.getDirection().y, 0.00001);
    }

    @Test
    public void testCreateLineThroughTwoPoints() {
        final Vec2d p1 = new Vec2d(5, 6);
        final Vec2d p2 = new Vec2d(3, 2);
        final Vec2d direction = p2.sub(p1).normalize();
        final Line line = Line.createLineThroughTwoPoints(p1, p2);
        Assert.assertEquals(5.0, line.getPointOnLine().x, 0.00001);
        Assert.assertEquals(6.0, line.getPointOnLine().y, 0.00001);
        Assert.assertEquals(direction.x, line.getDirection().x, 0.00001);
        Assert.assertEquals(direction.y, line.getDirection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_intersecting() {
        final Intersection iLine1Line2 = line1.intersect(line2);
        Assert.assertTrue(iLine1Line2.isIntersecting());
        Assert.assertEquals(4.0, iLine1Line2.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, iLine1Line2.getIntersection().y, 0.00001);

        final Intersection iLine2Line1 = line2.intersect(line1);
        Assert.assertTrue(iLine2Line1.isIntersecting());
        Assert.assertEquals(4.0, iLine2Line1.getIntersection().x, 0.00001);
        Assert.assertEquals(4.0, iLine2Line1.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_nonIntersecting() {
        final Intersection iLine2Line3 = line2.intersect(line3);
        Assert.assertFalse(iLine2Line3.isIntersecting());
        Assert.assertNull(iLine2Line3.getIntersection());

        final Intersection iLine3Line2 = line3.intersect(line2);
        Assert.assertFalse(iLine3Line2.isIntersecting());
        Assert.assertNull(iLine3Line2.getIntersection());
    }

    @Test
    public void test_intersectionWithLineSegment_intersecting() {
        final Intersection i = line3.intersect(lineSegment);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(2.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(1.5, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_nonIntersecting() {
        final Intersection i = line2.intersect(lineSegment);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
    }

    @Test
    public void test_intersectionWithRay_intersecting() {
        final Intersection i = line2.intersect(ray);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(4.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(5.0, i.getIntersection().y, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_nonIntersecting() {
        final Intersection i = line3.intersect(ray);
        Assert.assertFalse(i.isIntersecting());
        Assert.assertNull(i.getIntersection());
    }

    @Test
    public void test_distanceTo() {
        final double distance1 = line3.distanceTo(new Vec2d(2, 2));
        Assert.assertEquals(0.0, distance1, 0.00001);

        final double distance2 = line3.distanceTo(new Vec2d(-1, 5));
        Assert.assertEquals(3.0, distance2, 0.00001);

        final double distance3 = line3.distanceTo(new Vec2d(4, 0));
        Assert.assertEquals(2.0, distance3, 0.00001);
    }
}
