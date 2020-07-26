package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Plane {

    private final Vec3d pointOnPlane;

    private final Vec3d normal;

    public Plane(Vec3d pointOnPlane, Vec3d normal) {
        this.pointOnPlane = pointOnPlane;
        this.normal = normal.normalize();
    }

    public Plane(Vec3d p1, Vec3d p2, Vec3d p3) {
        final Vec3d a = p2.sub(p1);
        final Vec3d b = p3.sub(p1);
        normal = a.cross(b).normalize();
        pointOnPlane = p1;
    }

    public Intersection intersect(Line line) {
        final double numerator = pointOnPlane.sub(line.getPointOnLine()).dot(normal);
        final double denominator = line.getDirection().dot(normal);
        if (denominator != 0) {
            final double lambda = numerator / denominator;
            return new Intersection(line.getPointOnLine().add(line.getDirection().scale(lambda)));
        } else {
            return new Intersection();
        }
    }

    public Intersection intersect(LineSegment lineSegment) {
        final double numerator = pointOnPlane.sub(lineSegment.getStart()).dot(normal);
        final double denominator = lineSegment.getDirection().dot(normal);
        if (denominator != 0) {
            final double lambda = numerator / denominator;
            if(lambda >= 0 && lambda <= lineSegment.getLength()) {
                return new Intersection(lineSegment.getStart().add(lineSegment.getDirection().scale(lambda)));
            }
        }
        return new Intersection();
    }

    public Intersection intersect(Ray ray) {
        final double numerator = pointOnPlane.sub(ray.getOrigin()).dot(normal);
        final double denominator = ray.getDirection().dot(normal);
        if (denominator != 0) {
            final double lambda = numerator / denominator;
            if(lambda >= 0) {
                return new Intersection(ray.getOrigin().add(ray.getDirection().scale(lambda)));
            }
        }
        return new Intersection();
    }

    /*
     * Returns 1 if the point is on the front side of the plane,
     * -1 if the point is on the back side and 0 if it lies exactly on the plane.
     */
    public double getSideOfPlane(Vec3d point) {
        return Math.signum(point.sub(pointOnPlane).dot(normal));
    }

    public double getDistance(Vec3d point) {
        return Math.abs(point.sub(pointOnPlane).dot(normal));
    }

    public Vec3d getPointOnPlane() {
        return pointOnPlane;
    }

    public Vec3d getNormal() {
        return normal;
    }

}
