package de.ulfbiallas.tyreogem.core.math;

import java.util.List;

public class Vec2d {

    public final double x;

    public final double y;

    public static Vec2d zero() {
        return new Vec2d(0.0, 0.0);
    }

    public static Vec2d e1() {
        return new Vec2d(1.0, 0.0);
    }

    public static Vec2d e2() {
        return new Vec2d(0.0, 1.0);
    }

    public static Vec2d arithmeticMean(List<Vec2d> points) {
        return points.stream().reduce(Vec2d.zero(), (v1, v2) -> v1.add(v2)).scale(1.0 / ((double) points.size()));
    }

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2d(Vec2d v) {
        this.x = v.x;
        this.y = v.y;
    }

    public Vec2d add(Vec2d v) {
        return new Vec2d(x + v.x, y + v.y);
    }

    public Vec2d sub(Vec2d v) {
        return new Vec2d(x - v.x, y - v.y);
    }

    public double dot(Vec2d v) {
        return (x * v.x) + (y * v.y);
    }

    public Vec2d scale(double s) {
        return new Vec2d(x * s, y * s);
    }

    public Vec2d negate() {
        return new Vec2d(-x, -y);
    }

    public double norm2() {
        return x * x + y * y;
    }

    public double norm() {
        return Math.sqrt(x * x + y * y);
    }

    public Vec2d normalize() {
        return this.scale(1.0f / this.norm());
    }

    public double[] toArray() {
        return new double[]{x, y};
    }

    @Override
    public String toString() {
        return "["+ x + ", " + y + "]";
    }

}
