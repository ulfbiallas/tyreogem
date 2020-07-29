package de.ulfbiallas.tyreogem.core.planar;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

public class Intersection {

    private final boolean intersecting;

    private final Vec2d intersection;

    public Intersection() {
        this.intersecting = false;
        this.intersection = null;
    }

    public Intersection(Vec2d intersection) {
        this.intersecting = true;
        this.intersection = intersection;
    }

    public Vec2d getIntersection() {
        return intersection;
    }

    public boolean isIntersecting() {
        return intersecting;
    }

}
