package de.ulfbiallas.tyreogem.core.spatial;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Sphere {

    private final Vec3d center;

    private final double radius;

    public Sphere(Vec3d center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    public Boolean isPointInside(Vec3d p) {
        return p.sub(center).norm2() <= radius*radius;
    }

    public Vec3d getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    public double getDiameter() {
        return 2*radius;
    }

    public double getCircumference() {
        return Math.PI * getDiameter();
    }

    public MultiIntersection<Intersection> intersect(Line line) {
        final Ray ray = new Ray(line.getPointOnLine(), line.getDirection());
        final List<Double> parameters = getIntersectionParameters(ray);
        final List<Intersection> intersections = parameters.stream().map(p -> ray.getPoint(p)).map(p -> new Intersection(p)).collect(Collectors.toList());
        return new MultiIntersection<Intersection>(intersections);
    }

    public MultiIntersection<Intersection> intersect(LineSegment lineSegment) {
        final Ray ray = new Ray(lineSegment.getStart(), lineSegment.getDirection());
        final List<Double> parameters = getIntersectionParameters(ray);
        final List<Intersection> intersections = parameters.stream().filter(p -> p >= 0 && p <= lineSegment.getLength()).map(p -> ray.getPoint(p)).map(p -> new Intersection(p)).collect(Collectors.toList());
        return new MultiIntersection<Intersection>(intersections); 
    }

    public MultiIntersection<RayIntersection> intersect(Ray ray) {
        final List<Double> parameters = getIntersectionParameters(ray);
        final List<RayIntersection> intersections = parameters.stream().filter(p -> p >= 0).map(p -> new RayIntersection(ray.getPoint(p), p)).collect(Collectors.toList());
        return new MultiIntersection<RayIntersection>(intersections); 
    }

    private List<Double> getIntersectionParameters(Ray ray) {
        final Vec3d c = this.center;
        final double r = this.radius;
        final Vec3d p = ray.getOrigin();
        final Vec3d d = ray.getDirection();
        final Vec3d e = p.sub(c);

        final double aa = d.y * d.y + d.x * d.x;
        final double bb = 2 * (e.x * d.x + e.y * d.y);
        final double cc = e.x * e.x + e.y * e.y - r*r;

        final double discriminant = bb * bb - 4 * aa * cc;
        if(discriminant < 0) {
            return Arrays.asList();
        } else if(discriminant > 0) {
            final double x1 = 1.0 / (2.0 * aa) * (-bb + Math.sqrt(discriminant));
            final double x2 = 1.0 / (2.0 * aa) * (-bb - Math.sqrt(discriminant));
            return Arrays.asList(Math.min(x1, x2), Math.max(x1, x2));
        } else {
            final double x = 1.0 / (2.0 * aa) * (-bb + Math.sqrt(discriminant));
            return Arrays.asList(x);
        }
    }

}
