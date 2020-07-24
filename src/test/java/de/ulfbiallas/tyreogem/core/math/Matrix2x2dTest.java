package de.ulfbiallas.tyreogem.core.math;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class Matrix2x2dTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructor() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Assert.assertEquals(3.0, m.getA(), 0.00001);
        Assert.assertEquals(2.0, m.getB(), 0.00001);
        Assert.assertEquals(5.0, m.getC(), 0.00001);
        Assert.assertEquals(4.0, m.getD(), 0.00001);
    }

    @Test
    public void testConstructor2() {
        Vec2d v1 = new Vec2d(3.0, 5.0);
        Vec2d v2 = new Vec2d(2.0, 4.0);
        Matrix2x2d m = new Matrix2x2d(v1, v2);
        Assert.assertEquals(3.0, m.getA(), 0.00001);
        Assert.assertEquals(2.0, m.getB(), 0.00001);
        Assert.assertEquals(5.0, m.getC(), 0.00001);
        Assert.assertEquals(4.0, m.getD(), 0.00001);
    }

    @Test
    public void testConstructorZero() {
        Matrix2x2d m = Matrix2x2d.zero();
        Assert.assertEquals(0.0, m.getA(), 0.00001);
        Assert.assertEquals(0.0, m.getB(), 0.00001);
        Assert.assertEquals(0.0, m.getC(), 0.00001);
        Assert.assertEquals(0.0, m.getD(), 0.00001);
    }

    @Test
    public void testConstructorIdentity() {
        Matrix2x2d m = Matrix2x2d.identity();
        Assert.assertEquals(1.0, m.getA(), 0.00001);
        Assert.assertEquals(0.0, m.getB(), 0.00001);
        Assert.assertEquals(0.0, m.getC(), 0.00001);
        Assert.assertEquals(1.0, m.getD(), 0.00001);
    }

    @Test
    public void testGetColumns() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Vec2d c1 = m.get1stColumn();
        Vec2d c2 = m.get2ndColumn();
        Assert.assertEquals(3.0, c1.x, 0.00001);
        Assert.assertEquals(5.0, c1.y, 0.00001);
        Assert.assertEquals(2.0, c2.x, 0.00001);
        Assert.assertEquals(4.0, c2.y, 0.00001);
    }

    @Test
    public void testTranspose() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d t = m.transpose();
        Assert.assertEquals(3.0, t.getA(), 0.00001);
        Assert.assertEquals(5.0, t.getB(), 0.00001);
        Assert.assertEquals(2.0, t.getC(), 0.00001);
        Assert.assertEquals(4.0, t.getD(), 0.00001);
    }

    @Test
    public void testInvert() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d i = m.invert();
        Assert.assertEquals(2.0, i.getA(), 0.00001);
        Assert.assertEquals(-1.0, i.getB(), 0.00001);
        Assert.assertEquals(-2.5, i.getC(), 0.00001);
        Assert.assertEquals(1.5, i.getD(), 0.00001);
    }

    @Test
    public void testInvertWithSingularMatrix() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Matrix is singular!");
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 6.0, 4.0);
        m.invert();
    }

    @Test
    public void solve() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Vec2d v = m.solve(new Vec2d(-3, 2));
        Assert.assertEquals(-8.0, v.x, 0.00001);
        Assert.assertEquals(10.5, v.y, 0.00001);
    }

    @Test
    public void solveWithSingularMatrix() {
        exception.expect(RuntimeException.class);
        exception.expectMessage("Matrix is singular!");
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 6.0, 4.0);
        m.solve(new Vec2d(-3, 2));
    }

    @Test
    public void testScale() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d s = m.scale(-3);
        Assert.assertEquals(-9.0, s.getA(), 0.00001);
        Assert.assertEquals(-6.0, s.getB(), 0.00001);
        Assert.assertEquals(-15.0, s.getC(), 0.00001);
        Assert.assertEquals(-12.0, s.getD(), 0.00001);
    }

    @Test
    public void testAdd() {
        Matrix2x2d m1 = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d m2 = new Matrix2x2d(2.0, -1.0, -1.0, 3.0);
        Matrix2x2d m = m1.add(m2);
        Assert.assertEquals(5.0, m.getA(), 0.00001);
        Assert.assertEquals(1.0, m.getB(), 0.00001);
        Assert.assertEquals(4.0, m.getC(), 0.00001);
        Assert.assertEquals(7.0, m.getD(), 0.00001);
    }

    @Test
    public void testSub() {
        Matrix2x2d m1 = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d m2 = new Matrix2x2d(2.0, -1.0, -1.0, 3.0);
        Matrix2x2d m = m1.sub(m2);
        Assert.assertEquals(1.0, m.getA(), 0.00001);
        Assert.assertEquals(3.0, m.getB(), 0.00001);
        Assert.assertEquals(6.0, m.getC(), 0.00001);
        Assert.assertEquals(1.0, m.getD(), 0.00001);
    }

    @Test
    public void testTimesMatrix() {
        Matrix2x2d m1 = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Matrix2x2d m2 = new Matrix2x2d(2.0, -1.0, -1.0, 3.0);
        Matrix2x2d m = m1.times(m2);
        Assert.assertEquals(4.0, m.getA(), 0.00001);
        Assert.assertEquals(3.0, m.getB(), 0.00001);
        Assert.assertEquals(6.0, m.getC(), 0.00001);
        Assert.assertEquals(7.0, m.getD(), 0.00001);
    }

    @Test
    public void testTimesVec2() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        Vec2d v = m.times(new Vec2d(2.0, -1.0));
        Assert.assertEquals(4.0, v.x, 0.00001);
        Assert.assertEquals(6.0, v.y, 0.00001);
    }

    @Test
    public void testDeterminant() {
        Matrix2x2d m = new Matrix2x2d(3.0, 2.0, 5.0, 4.0);
        double d = m.getDeterminant();
        Assert.assertEquals(2.0, d, 0.00001);
    }

}
