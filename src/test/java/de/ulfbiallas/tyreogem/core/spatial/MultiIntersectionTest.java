package de.ulfbiallas.tyreogem.core.spatial;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class MultiIntersectionTest {

    @Test
    public void test_constructor_intersecting() {
        final Intersection intersection0 = new Intersection(new Vec3d(2, -5, 1));
        final Intersection intersection1 = new Intersection();
        final Intersection intersection2 = new Intersection(new Vec3d(4, 3, 2));
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
