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

}
