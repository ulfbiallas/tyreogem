package de.ulfbiallas.tyreogem.core.spatial;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class LineSegmentTest {

    private static final LineSegment lineSegment = new LineSegment(new Vec3d(3, 2, -1), new Vec3d(3, 5, -1));

    @Test
    public void test_constructor() {
        final LineSegment lineSegment = new LineSegment(new Vec3d(5, 6, -2), new Vec3d(3, 2, 0));
        Assert.assertEquals(5.0, lineSegment.getStart().x, 0.00001);
        Assert.assertEquals(6.0, lineSegment.getStart().y, 0.00001);
        Assert.assertEquals(-2.0, lineSegment.getStart().z, 0.00001);
        Assert.assertEquals(3.0, lineSegment.getEnd().x, 0.00001);
        Assert.assertEquals(2.0, lineSegment.getEnd().y, 0.00001);
        Assert.assertEquals(0.0, lineSegment.getEnd().z, 0.00001);
    }

    @Test
    public void test_getDirection() {
        final LineSegment lineSegment = new LineSegment(new Vec3d(4, 2, -1), new Vec3d(8, 2, -1));
        Assert.assertEquals(1.0, lineSegment.getDirection().x, 0.00001);
        Assert.assertEquals(0.0, lineSegment.getDirection().y, 0.00001);
        Assert.assertEquals(0.0, lineSegment.getDirection().z, 0.00001);
    }

    @Test
    public void test_getLength() {
        final LineSegment lineSegment = new LineSegment(new Vec3d(4, 2, -1), new Vec3d(8, 2, -1));
        Assert.assertEquals(4.0, lineSegment.getLength(), 0.00001);
    }

    @Test
    public void test_intersectionWithSphere_intersecting() {
        final Sphere sphere = new Sphere(new Vec3d(3, 2.5, -1), 1.0);
        final MultiIntersection<Intersection> intersections = lineSegment.intersect(sphere);
        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(3.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(3.5, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-1.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_distanceTo() {
        final double distance1 = lineSegment.distanceTo(new Vec3d(3, 4, -1));
        Assert.assertEquals(0.0, distance1, 0.00001);

        final double distance2 = lineSegment.distanceTo(new Vec3d(0, 5, -1));
        Assert.assertEquals(3.0, distance2, 0.00001);

        final double distance3 = lineSegment.distanceTo(new Vec3d(3, 9, -1));
        Assert.assertEquals(4.0, distance3, 0.00001);

        final double distance4 = lineSegment.distanceTo(new Vec3d(3, -5, -1));
        Assert.assertEquals(7.0, distance4, 0.00001);
    }

}
