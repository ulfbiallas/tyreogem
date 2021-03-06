package de.ulfbiallas.tyreogem.core.spatial;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class LineTest {

    private static final Line line = Line.createLineThroughTwoPoints(new Vec3d(3, 2, -1), new Vec3d(3, 5, -1));

    @Test
    public void testCreateLineWithPointAndDirection() {
        final Vec3d direction = new Vec3d(3, 2, 0);
        final Vec3d directionNormalized = direction.normalize();
        final Line line = Line.createLineWithPointAndDirection(new Vec3d(5, 6, -2), new Vec3d(3, 2, 0));
        Assert.assertEquals(5.0, line.getPointOnLine().x, 0.00001);
        Assert.assertEquals(6.0, line.getPointOnLine().y, 0.00001);
        Assert.assertEquals(-2.0, line.getPointOnLine().z, 0.00001);
        Assert.assertEquals(directionNormalized.x, line.getDirection().x, 0.00001);
        Assert.assertEquals(directionNormalized.y, line.getDirection().y, 0.00001);
        Assert.assertEquals(directionNormalized.z, line.getDirection().z, 0.00001);
    }

    @Test
    public void testCreateLineWithPointAndDirection_invalidDirection() {
        assertThatThrownBy(() -> {
            final Vec3d direction = new Vec3d(0, 0, 0);
            Line.createLineWithPointAndDirection(new Vec3d(5, 6, -2), direction);
        }).hasMessage("Direction must not be a null vector!");
    }

    @Test
    public void testCreateLineThroughTwoPoints() {
        final Vec3d p1 = new Vec3d(5, -6, 3);
        final Vec3d p2 = new Vec3d(-3, 2, 1);
        final Vec3d direction = p2.sub(p1).normalize();
        final Vec3d directionNormalized = direction.normalize();
        final Line line = Line.createLineThroughTwoPoints(p1, p2);
        Assert.assertEquals(5.0, line.getPointOnLine().x, 0.00001);
        Assert.assertEquals(-6.0, line.getPointOnLine().y, 0.00001);
        Assert.assertEquals(3.0, line.getPointOnLine().z, 0.00001);
        Assert.assertEquals(directionNormalized.x, line.getDirection().x, 0.00001);
        Assert.assertEquals(directionNormalized.y, line.getDirection().y, 0.00001);
        Assert.assertEquals(directionNormalized.z, line.getDirection().z, 0.00001);
    }

    @Test
    public void testCreateLineThroughTwoPoints_invalidDirection() {
        assertThatThrownBy(() -> {
            final Vec3d p1 = new Vec3d(5, -6, 3);
            final Vec3d p2 = new Vec3d(5, -6, 3);
            Line.createLineThroughTwoPoints(p1, p2);
        }).hasMessage("Direction must not be a null vector!");
    }

    @Test
    public void test_intersectionWithSphere_intersecting() {
        final Sphere sphere = new Sphere(new Vec3d(3, -4, -1), 3);
        final MultiIntersection<Intersection> intersections = line.intersect(sphere);
        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        Intersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(3, i1.getIntersection().x, 0.01);
        Assert.assertEquals(-7, i1.getIntersection().y, 0.01);
        Assert.assertEquals(-1, i1.getIntersection().z, 0.01);

        Intersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(3, i2.getIntersection().x, 0.01);
        Assert.assertEquals(-1, i2.getIntersection().y, 0.01);
        Assert.assertEquals(-1, i2.getIntersection().z, 0.01);
    }

    @Test
    public void test_distanceTo() {
        final double distance1 = line.distanceTo(new Vec3d(3, 4, -1));
        Assert.assertEquals(0.0, distance1, 0.00001);

        final double distance2 = line.distanceTo(new Vec3d(0, 5, -1));
        Assert.assertEquals(3.0, distance2, 0.00001);

        final double distance3 = line.distanceTo(new Vec3d(3, 9, 1));
        Assert.assertEquals(2.0, distance3, 0.00001);
    }

}
