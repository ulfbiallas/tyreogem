package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class RayIntersection extends Intersection {

    private final double parameter;

    public RayIntersection() {
        super();
        this.parameter = 0;
    }

    public RayIntersection(Vec2d intersection, double parameter) {
        super(intersection);
        this.parameter = parameter;
    }

    public double getParameter() {
        return parameter;
    }

}
