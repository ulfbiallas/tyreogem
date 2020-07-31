package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;
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
