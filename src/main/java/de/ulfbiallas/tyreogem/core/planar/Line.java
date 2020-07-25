package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class Line {

    private final Vec2d pointOnLine;

    private final Vec2d direction;

    private Line(Vec2d pointOnLine, Vec2d direction) {
        this.pointOnLine = pointOnLine;
        this.direction = direction;
    }

    public static Line createLineWithPointAndDirection(Vec2d pointOnLine, Vec2d direction) {
        return new Line(pointOnLine, direction);
    }

    public static Line createLineThroughTwoPoints(Vec2d p1, Vec2d p2) {
        return new Line(p1, p2.sub(p1).normalize());
    }

    public Vec2d getPointOnLine() {
        return pointOnLine;
    }

    public Vec2d getDirection() {
        return direction;
    }

    public Intersection intersect(Line line) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, line.getDirection().negate());
        final Vec2d r = line.getPointOnLine().sub(pointOnLine);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            return new Intersection(true, pointOnLine.add(direction.scale(lambda)));
        }
        return new Intersection();
    }

    public Intersection intersect(LineSegment lineSegment) {
        return lineSegment.intersect(this);
    }

    public Intersection intersect(Ray ray) {
        return ray.intersect(this);
    }

}
