package de.ulfbiallas.tyreogem.core.math;

public class Matrix2x2d {

    private final double a;

    private final double b;

    private final double c;

    private final double d;

    private final double determinant;

    public static Matrix2x2d zero() {
        return new Matrix2x2d(0.0, 0.0, 0.0, 0.0);
    }

    public static Matrix2x2d identity() {
        return new Matrix2x2d(1.0, 0.0, 0.0, 1.0);
    }

    public Matrix2x2d(Vec2d v1, Vec2d v2) {
        this(v1.x, v2.x, v1.y, v2.y);
    }

    public Matrix2x2d(double a, double b, double c, double d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.determinant = a * d - b * c;
    }

    public boolean isSingular() {
        return determinant == 0;
    }

    public Matrix2x2d transpose() {
        return new Matrix2x2d(a, c, b, d);
    }

    public Matrix2x2d invert() {
        if (isSingular()) {
            throw new RuntimeException("Matrix is singular!");
        }
        return new Matrix2x2d(d, -b, -c, a).scale(1.0 / determinant);
    }

    public Vec2d solve(Vec2d x) {
        if (isSingular()) {
            throw new RuntimeException("Matrix is singular!");
        }
        return invert().times(x);
    }

    public Matrix2x2d scale(double s) {
        return new Matrix2x2d(a * s, b * s, c * s, d * s);
    }

    public Vec2d times(Vec2d v) {
        return new Vec2d(a * v.x + b * v.y, c * v.x + d * v.y);
    }

    public Matrix2x2d times(Matrix2x2d matrix) {
        return new Matrix2x2d( //
                a * matrix.getA() + b * matrix.getC(), //
                a * matrix.getB() + b * matrix.getD(), //
                c * matrix.getA() + d * matrix.getC(), //
                c * matrix.getB() + d * matrix.getD() //
        );
    }

    public Matrix2x2d add(Matrix2x2d matrix) {
        return new Matrix2x2d(a + matrix.getA(), b + matrix.getB(), c + matrix.getC(), d + matrix.getD());
    }

    public Matrix2x2d sub(Matrix2x2d matrix) {
        return new Matrix2x2d(a - matrix.getA(), b - matrix.getB(), c - matrix.getC(), d - matrix.getD());
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getC() {
        return c;
    }

    public double getD() {
        return d;
    }

    public Vec2d get1stColumn() {
        return new Vec2d(a, c);
    }

    public Vec2d get2ndColumn() {
        return new Vec2d(b, d);
    }

    public double getDeterminant() {
        return determinant;
    }

    @Override
    public String toString() {
        return "[" + a + "," + b + ";" + c + "," + d + "]";
    }

}
