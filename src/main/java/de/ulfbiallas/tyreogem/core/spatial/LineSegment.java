package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class LineSegment {

    private final Vec3d start;

    private final Vec3d end;

    private final Vec3d direction;

    private final double length;

    public LineSegment(Vec3d start, Vec3d end) {
        this.start = start;
        this.end = end;
        final Vec3d connection = end.sub(start);
        this.direction = connection.normalize();
        this.length = connection.norm();
    }

    public Vec3d getStart() {
        return start;
    }

    public Vec3d getEnd() {
        return end;
    }

    public Vec3d getDirection() {
        return direction;
    }

    public double getLength() {
        return length;
    }

    public MultiIntersection<Intersection> intersect(Sphere sphere) {
        return sphere.intersect(this);
    }

    public double distanceTo(Vec3d point) {
        final Ray rayFromStart = new Ray(start, direction);
        final Ray rayFromEnd = new Ray(end, direction.negate());
        final Plane plane = new Plane(point, direction);
        final Intersection intersectionWithRayFromStart = plane.intersect(rayFromStart);
        final Intersection intersectionWithRayFromEnd = plane.intersect(rayFromEnd);
        if(intersectionWithRayFromStart.isIntersecting()) {
            if(intersectionWithRayFromEnd.isIntersecting()) {
                return point.distanceTo(intersectionWithRayFromStart.getIntersection());
            } else {
                return point.distanceTo(end);
            }
        } else {
            return point.distanceTo(start);
        }
    }

}
