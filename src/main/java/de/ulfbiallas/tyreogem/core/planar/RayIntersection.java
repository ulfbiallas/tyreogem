package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class RayIntersection extends Intersection {

    private final double parameter;

    public RayIntersection() {
        super(false, null);
        this.parameter = 0;
    }

    public RayIntersection(Boolean intersecting, Vec2d intersection, double parameter) {
        super(intersecting, intersection);
        this.parameter = parameter;
    }

    public double getParameter() {
        return parameter;
    }

}
