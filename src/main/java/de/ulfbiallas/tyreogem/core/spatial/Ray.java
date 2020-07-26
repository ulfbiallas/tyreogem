package de.ulfbiallas.tyreogem.core.spatial;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Ray {

    private final Vec3d origin;

    private final Vec3d direction;

    public Ray(Vec3d origin, Vec3d direction) {
        this.origin = origin;
        this.direction = direction;
    }

    public Vec3d getOrigin() {
        return origin;
    }

    public Vec3d getDirection() {
        return direction;
    }

}
