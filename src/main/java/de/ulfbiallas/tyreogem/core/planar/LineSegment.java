package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class LineSegment {

    private final Vec2d start;

    private final Vec2d end;

    private final Vec2d direction;

    private final double length;

    public LineSegment(Vec2d start, Vec2d end) {
        this.start = start;
        this.end = end;
        final Vec2d connection = end.sub(start);
        this.direction = connection.normalize();
        this.length = connection.norm();
    }

    public Vec2d getStart() {
        return start;
    }

    public Vec2d getEnd() {
        return end;
    }

    public Vec2d getDirection() {
        return direction;
    }

    public double getLength() {
        return length;
    }

    public Intersection intersect(LineSegment lineSegment) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, lineSegment.getDirection().negate());
        final Vec2d r = lineSegment.getStart().sub(start);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            final double mu = result.y;
            if(lambda >= 0 && lambda <= length && mu >= 0 && mu <= lineSegment.getLength()) {
                return new Intersection(true, start.add(direction.scale(lambda)));
            }
        }
        return new Intersection();
    }

    public Intersection intersect(Line line) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, line.getDirection().negate());
        final Vec2d r = line.getPointOnLine().sub(start);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            if(lambda >= 0 && lambda <= length) {
                return new Intersection(true, start.add(direction.scale(lambda)));
            }
        }
        return new Intersection();
    }

    public Intersection intersect(Ray ray) {
        return ray.intersect(this);
    }

    public double distanceTo(Vec2d point) {
        final Ray lineSegmentRay = new Ray(getStart(), getDirection());
        final Line line = Line.createLineWithPointAndDirection(point, getDirection().perpendicular());
        final RayIntersection intersection = lineSegmentRay.intersect(line);
        if(intersection.isIntersecting()) {
            if(intersection.getParameter() <= getLength()) {
                return point.distanceTo(intersection.getIntersection());
            } else {
                return point.distanceTo(getEnd());
            }
        } else {
            return point.distanceTo(getStart());
        }
    }

}
