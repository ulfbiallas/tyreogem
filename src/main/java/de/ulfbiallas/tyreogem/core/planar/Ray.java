package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class Ray {

    private final Vec2d origin;

    private final Vec2d direction;

    public Ray(Vec2d origin, Vec2d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec2d getOrigin() {
        return origin;
    }

    public Vec2d getDirection() {
        return direction;
    }

    public Vec2d getPoint(double parameter) {
        return origin.add(direction.scale(parameter));
    }

    public RayIntersection intersect(Ray ray) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, ray.getDirection().negate());
        final Vec2d r = ray.getOrigin().sub(origin);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            final double mu = result.y;
            if(lambda >= 0 && mu >= 0) {
                return new RayIntersection(origin.add(direction.scale(lambda)), lambda);
            }
        }
        return new RayIntersection();
    }

    public RayIntersection intersect(Line line) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, line.getDirection().negate());
        final Vec2d r = line.getPointOnLine().sub(origin);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            if(lambda >= 0) {
                return new RayIntersection(origin.add(direction.scale(lambda)), lambda);
            }
        }
        return new RayIntersection();
    }

    public RayIntersection intersect(LineSegment lineSegment) {
        final Matrix2x2d matrix = new Matrix2x2d(direction, lineSegment.getDirection().negate());
        final Vec2d r = lineSegment.getStart().sub(origin);

        if(!matrix.isSingular()) {
            final Vec2d result = matrix.solve(r);
            final double lambda = result.x;
            final double mu = result.y;
            if(lambda >= 0 && mu >= 0 && mu <= lineSegment.getLength()) {
                return new RayIntersection(origin.add(direction.scale(lambda)), lambda);
            }
        }
        return new RayIntersection();
    }

    public MultiIntersection<RayIntersection> intersect(Circle circle) {
        return circle.intersect(this);
    }

    public double distanceTo(Vec2d point) {
        final Line line = Line.createLineWithPointAndDirection(point, getDirection().perpendicular());
        final RayIntersection intersection = intersect(line);
        if(intersection.isIntersecting()) {
            return point.distanceTo(intersection.getIntersection());
        } else {
            return point.distanceTo(getOrigin());
        }
    }

}
