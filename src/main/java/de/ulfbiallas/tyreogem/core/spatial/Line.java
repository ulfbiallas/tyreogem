package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Line {

    private final Vec3d pointOnLine;

    private final Vec3d direction;

    private Line(Vec3d pointOnLine, Vec3d direction) {
        if(direction.norm2() == 0) {
            throw new RuntimeException("Direction must not be a null vector!");
        }
        this.pointOnLine = pointOnLine;
        this.direction = direction.normalize();
    }

    public static Line createLineWithPointAndDirection(Vec3d pointOnLine, Vec3d direction) {
        return new Line(pointOnLine, direction);
    }

    public static Line createLineThroughTwoPoints(Vec3d p1, Vec3d p2) {
        return new Line(p1, p2.sub(p1));
    }

    public Vec3d getPointOnLine() {
        return pointOnLine;
    }

    public Vec3d getDirection() {
        return direction;
    }

    public double distanceTo(Vec3d point) {
        final Plane plane = new Plane(point, direction);
        final Intersection intersection = plane.intersect(this);
        return point.distanceTo(intersection.getIntersection());
    }

}
