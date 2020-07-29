package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Triangle {

    private final Vec3d a;

    private final Vec3d b;

    private final Vec3d c;

    public Triangle(Vec3d a, Vec3d b, Vec3d c) {
        this.a = a;
        this.b = b;
        this.c = c;
    }

    public Vec3d getA() {
        return a;
    }

    public Vec3d getB() {
        return b;
    }

    public Vec3d getC() {
        return c;
    }

    public Plane getPlane() {
        return new Plane(a, b, c);
    }

    private boolean isPointOnPlaneInside(Vec3d point) {
        final Vec3d r1 = b.sub(a);
        final Vec3d r2 = c.sub(a);
        final Vec3d pa = point.sub(a);

        final double lambda = r1.dot(pa);
        final double mu = r2.dot(pa);

        return mu >= 0 && lambda >= 0 && mu+lambda <= 1;
    }

    private boolean isIntersectionInsideTriangle(Intersection planeIntersection) {
        if(planeIntersection.isIntersecting()) {
            final Vec3d x = planeIntersection.getIntersection();
            if(isPointOnPlaneInside(x)) {
                return true;
            }
        }
        return false;
    }

    public Intersection intersect(Line line) {
        final Intersection planeIntersection = getPlane().intersect(line);
        if(isIntersectionInsideTriangle(planeIntersection)) {
            return planeIntersection;
        }
        return new Intersection();
    }

    public Intersection intersect(LineSegment lineSegment) {
        final Intersection planeIntersection = getPlane().intersect(lineSegment);
        if(isIntersectionInsideTriangle(planeIntersection)) {
            return planeIntersection;
        }
        return new Intersection();
    }


    public RayIntersection intersect(Ray ray) {
        final RayIntersection planeIntersection = getPlane().intersect(ray);
        if(isIntersectionInsideTriangle(planeIntersection)) {
            return planeIntersection;
        }
        return new RayIntersection();
    }

}
