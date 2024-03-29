package de.ulfbiallas.tyreogem.core.math;

import java.util.List;

import de.ulfbiallas.tyreogem.core.spatial.Plane;

public class Vec3d {

    public final double x;

    public final double y;

    public final double z;

    public static Vec3d zero() {
        return new Vec3d(0.0, 0.0, 0.0);
    }

    public static Vec3d e1() {
        return new Vec3d(1.0, 0.0, 0.0);
    }

    public static Vec3d e2() {
        return new Vec3d(0.0, 1.0, 0.0);
    }

    public static Vec3d e3() {
        return new Vec3d(0.0, 0.0, 1.0);
    }

    public static Vec3d arithmeticMean(List<Vec3d> points) {
        return points.stream().reduce(Vec3d.zero(), (v1, v2) -> v1.add(v2)).scale(1.0 / ((double) points.size()));
    }

    public Vec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vec3d(Vec3d v) {
        this.x = v.x;
        this.y = v.y;
        this.z = v.z;
    }

    @Override
    public Vec3d clone() {
        return new Vec3d(this.x, this.y, this.z);
    }

    public Vec3d add(Vec3d v) {
        return new Vec3d(x + v.x, y + v.y, z + v.z);
    }

    public Vec3d sub(Vec3d v) {
        return new Vec3d(x - v.x, y - v.y, z - v.z);
    }

    public double dot(Vec3d v) {
        return (x * v.x) + (y * v.y) + (z * v.z);
    }

    public Vec3d scale(double s) {
        return new Vec3d(x * s, y * s, z * s);
    }

    public Vec3d scaleComponentWise(Vec3d v) {
        return new Vec3d(x * v.x, y * v.y, z * v.z);
    }

    public Vec3d negate() {
        return new Vec3d(-x, -y, -z);
    }

    public double norm2() {
        return x * x + y * y + z * z;
    }

    public double norm() {
        return Math.sqrt(x * x + y * y + z * z);
    }

    public Vec3d normalize() {
        return this.scale(1.0f / this.norm());
    }

    public double distanceTo(Vec3d point) {
        return sub(point).norm();
    }

    public Vec3d cross(Vec3d v) {
        return new Vec3d(y * v.z - z * v.y, z * v.x - x * v.z, x * v.y - y * v.x);
    }

    public double[] toArray() {
        return new double[]{x, y, z};
    }

    public Vec3d mirrorByPoint(Vec3d point) {
        final Vec3d connection = this.sub(point);
        return point.sub(connection);
    }

    public Vec3d mirrorByPlane(Plane plane) {
        final double signedDistance = plane.signedDistanceTo(this);
        return this.sub(plane.getNormal().scale(2.0 * signedDistance));
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof Vec3d)) {
            Vec3d v = (Vec3d) obj;
            return v.x == this.x && v.y == this.y && v.z == this.z;
        }
        return false;
    }

    @Override
    public String toString() {
        return "["+ x + ", " + y + ", " + z+"]";
    }

}
