package de.ulfbiallas.tyreogem.core.planar;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;


public class MultiIntersectionTest {

    @Test
    public void test_constructor_intersecting() {
        final Intersection intersection0 = new Intersection(new Vec2d(2, -5));
        final Intersection intersection1 = new Intersection();
        final Intersection intersection2 = new Intersection(new Vec2d(4, 3));
        final MultiIntersection<Intersection> multiIntersection = new MultiIntersection<Intersection>(Arrays.asList(intersection0, intersection1, intersection2));
        Assert.assertTrue(multiIntersection.isIntersecting());
        Assert.assertEquals(2, multiIntersection.getNumberOfIntersections());
        Assert.assertEquals(intersection0, multiIntersection.getIntersections().get(0));
        Assert.assertEquals(intersection2, multiIntersection.getIntersections().get(1));
    }

    @Test
    public void test_constructor_nonIntersecting() {
        final Intersection intersection0 = new Intersection();
        final Intersection intersection1 = new Intersection();
        final MultiIntersection<Intersection> multiIntersection = new MultiIntersection<Intersection>(Arrays.asList(intersection0, intersection1));
        Assert.assertFalse(multiIntersection.isIntersecting());
        Assert.assertEquals(0, multiIntersection.getNumberOfIntersections());
    }

}
