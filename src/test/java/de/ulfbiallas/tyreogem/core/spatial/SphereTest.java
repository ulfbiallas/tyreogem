package de.ulfbiallas.tyreogem.core.spatial;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class SphereTest {

    private static final Line line = Line.createLineWithPointAndDirection(new Vec3d(0, 0, -3), new Vec3d(1, 0, 0));
    private static final LineSegment lineSegment = new LineSegment(new Vec3d(0, 0, -3), new Vec3d(10, 0, -3));
    private static final Ray ray = new Ray(new Vec3d(-1, 0, -3), new Vec3d(1, 0, 0));

    @Test
    public void test_constructor() {
        final Sphere sphere = new Sphere(new Vec3d(3, 2, -5), 4);
        Assert.assertEquals(3.0, sphere.getCenter().x, 0.00001);
        Assert.assertEquals(2.0, sphere.getCenter().y, 0.00001);
        Assert.assertEquals(-5.0, sphere.getCenter().z, 0.00001);
        Assert.assertEquals(4.0, sphere.getRadius(), 0.00001);
        Assert.assertEquals(8.0, sphere.getDiameter(), 0.00001);
        Assert.assertEquals(8.0*Math.PI, sphere.getCircumference(), 0.00001);
    }

    @Test
    public void test_isPointInside() {
        final Sphere sphere = new Sphere(new Vec3d(3, 2, -5), 4);

        Assert.assertTrue(sphere.isPointInside(new Vec3d(3, 2, -5)));
        Assert.assertTrue(sphere.isPointInside(new Vec3d(-1, 2, -5)));
        Assert.assertTrue(sphere.isPointInside(new Vec3d(7, 2, -5)));
        Assert.assertTrue(sphere.isPointInside(new Vec3d(3, -2, -5)));
        Assert.assertTrue(sphere.isPointInside(new Vec3d(3, 6, -5)));

        Assert.assertFalse(sphere.isPointInside(new Vec3d(-2, 2, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(8, 2, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(3, -3, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(3, 7, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(-1, -2, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(-1, 6, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(7, -2, -5)));
        Assert.assertFalse(sphere.isPointInside(new Vec3d(7, 6, -5)));
    }

    @Test
    public void test_intersectionWithLine_twoIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(5, 0, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(line);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        Intersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i1.getIntersection().z, 0.00001);

        Intersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i2.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_oneIntersection() {
        final Sphere sphere = new Sphere(new Vec3d(5, 2, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(line);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLine_zeroIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(5, 4, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(line);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithLineSegment_twoIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(5, 0, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        Intersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i1.getIntersection().z, 0.00001);

        Intersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i2.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_oneIntersection() {
        final Sphere sphere = new Sphere(new Vec3d(5, 2, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_oneIntersection2() {
        final Sphere sphere = new Sphere(new Vec3d(0, 0, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(lineSegment);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        Intersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(2.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithLineSegment_zeroIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(-3, 0, -3), 2);
        MultiIntersection<Intersection> intersections = sphere.intersect(lineSegment);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithRay_twoIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(5, 0, -3), 2);
        MultiIntersection<RayIntersection> intersections = sphere.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(2 , intersections.getNumberOfIntersections());

        RayIntersection i1 = intersections.getIntersections().get(0);
        Assert.assertTrue(i1.isIntersecting());
        Assert.assertEquals(4.0, i1.getParameter(), 0.00001);
        Assert.assertEquals(3.0, i1.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i1.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i1.getIntersection().z, 0.00001);

        RayIntersection i2 = intersections.getIntersections().get(1);
        Assert.assertTrue(i2.isIntersecting());
        Assert.assertEquals(8.0, i2.getParameter(), 0.00001);
        Assert.assertEquals(7.0, i2.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i2.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i2.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_oneIntersection() {
        final Sphere sphere = new Sphere(new Vec3d(5, 2, -3), 2);
        MultiIntersection<RayIntersection> intersections = sphere.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        RayIntersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(6.0, i.getParameter(), 0.00001);
        Assert.assertEquals(5.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_oneIntersection2() {
        final Sphere sphere = new Sphere(new Vec3d(0, 0, -3), 2);
        MultiIntersection<RayIntersection> intersections = sphere.intersect(ray);

        Assert.assertTrue(intersections.isIntersecting());
        Assert.assertEquals(1 , intersections.getNumberOfIntersections());

        RayIntersection i = intersections.getIntersections().get(0);
        Assert.assertTrue(i.isIntersecting());
        Assert.assertEquals(3.0, i.getParameter(), 0.00001);
        Assert.assertEquals(2.0, i.getIntersection().x, 0.00001);
        Assert.assertEquals(0.0, i.getIntersection().y, 0.00001);
        Assert.assertEquals(-3.0, i.getIntersection().z, 0.00001);
    }

    @Test
    public void test_intersectionWithRay_zeroIntersections() {
        final Sphere sphere = new Sphere(new Vec3d(5, 4, -3), 2);
        MultiIntersection<RayIntersection> intersections = sphere.intersect(ray);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }

    @Test
    public void test_intersectionWithRay_zeroIntersections2() {
        final Sphere sphere = new Sphere(new Vec3d(-4, 0, -3), 2);
        MultiIntersection<RayIntersection> intersections = sphere.intersect(ray);

        Assert.assertFalse(intersections.isIntersecting());
        Assert.assertEquals(0 , intersections.getNumberOfIntersections());
    }
}
