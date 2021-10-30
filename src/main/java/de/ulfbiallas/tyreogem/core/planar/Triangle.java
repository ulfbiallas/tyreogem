package de.ulfbiallas.tyreogem.core.planar;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class Triangle {

    private final Vec2d a;

    private final Vec2d b;

    private final Vec2d c;

    private final LineSegment ab;

    private final LineSegment bc;

    private final LineSegment ca;

    public Triangle(Vec2d a, Vec2d b, Vec2d c) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.ab = new LineSegment(a, b);
        this.bc = new LineSegment(b, c);
        this.ca = new LineSegment(c, a);
    }

    public Boolean isPointInside(Vec2d point) {
        final Vec2d r1 = b.sub(a);
        final Vec2d r2 = c.sub(a);
        final Vec2d pa = point.sub(a);

        final double ma = r1.dot(r1);
        final double mb = r1.dot(r2);
        final double mc = r2.dot(r1);
        final double md = r2.dot(r2);
        final double me = r1.dot(pa);
        final double mf = r2.dot(pa);

        final Matrix2x2d matrix = new Matrix2x2d(ma, mb, mc, md);
        final Vec2d result = matrix.solve(new Vec2d(me, mf));

        final double lambda = result.x;
        final double mu = result.y;

        return mu >= 0 && lambda >= 0 && mu+lambda <= 1;
    }

    public Vec2d getA() {
        return a;
    }

    public Vec2d getB() {
        return b;
    }

    public Vec2d getC() {
        return c;
    }

    public LineSegment getAB() {
        return ab;
    }

    public LineSegment getBC() {
        return bc;
    }

    public LineSegment getCA() {
        return ca;
    }

    public List<LineSegment> getEdges() {
        return Arrays.asList(ab, bc, ca);
    }

    public MultiIntersection<Intersection> intersect(Line line) {
        final List<LineSegment> edges = getEdges();
        final List<Intersection> intersections = edges.stream().map(edge -> edge.intersect(line)).collect(Collectors.toList());
        return new MultiIntersection<Intersection>(intersections);
    }

    public MultiIntersection<Intersection> intersect(LineSegment lineSegment) {
        final List<LineSegment> edges = getEdges();
        final List<Intersection> intersections = edges.stream().map(edge -> edge.intersect(lineSegment)).collect(Collectors.toList());
        return new MultiIntersection<Intersection>(intersections);
    }

    public MultiIntersection<RayIntersection> intersect(Ray ray) {
        final List<LineSegment> edges = getEdges();
        final List<RayIntersection> intersections = edges.stream().map(edge -> edge.intersect(ray)).filter(i -> i.isIntersecting()).collect(Collectors.toList());
        intersections.sort(Comparator.comparingDouble(RayIntersection::getParameter));
        return new MultiIntersection<RayIntersection>(intersections);
    }
}
