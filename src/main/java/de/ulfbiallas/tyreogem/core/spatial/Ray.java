package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Ray {

    private final Vec3d origin;

    private final Vec3d direction;

    public Ray(Vec3d origin, Vec3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3d getOrigin() {
        return origin;
    }

    public Vec3d getDirection() {
        return direction;
    }

    public Vec3d getPoint(double parameter) {
        return origin.add(direction.scale(parameter));
    }

    public MultiIntersection<RayIntersection> intersect(Sphere sphere) {
        return sphere.intersect(this);
    }

    public double distanceTo(Vec3d point) {
        final Plane plane = new Plane(point, direction);
        final Intersection intersection = plane.intersect(this);
        if(intersection.isIntersecting()) {
            return point.distanceTo(intersection.getIntersection());
        } else {
            return point.distanceTo(origin);
        }
    }

}
