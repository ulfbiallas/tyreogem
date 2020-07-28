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
