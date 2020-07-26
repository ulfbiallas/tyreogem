package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Intersection {

    private final boolean intersecting;

    private final Vec3d intersection;

    public Intersection() {
        this.intersecting = false;
        this.intersection = null;
    }

    public Intersection(Vec3d intersection) {
        this.intersecting = true;
        this.intersection = intersection;
    }

    public Vec3d getIntersection() {
        return intersection;
    }

    public boolean isIntersecting() {
        return intersecting;
    }

}
