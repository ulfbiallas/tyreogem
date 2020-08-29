package de.ulfbiallas.tyreogem.mesh;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class Vertex {

    private final int pointIndex;

    private final Vec3d normal;

    private final Vec2d textureCoordinates;

    public Vertex(int pointIndex, Vec3d normal, Vec2d textureCoordinates) {
        this.pointIndex = pointIndex;
        this.normal = normal;
        this.textureCoordinates = textureCoordinates;
    }

    public int getPointIndex() {
        return pointIndex;
    }

    public Vec3d getNormal() {
        return normal;
    }

    public Vec2d getTextureCoordinates() {
        return textureCoordinates;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Vertex other = (Vertex) obj;
        if (normal == null) {
            if (other.normal != null)
                return false;
        } else if (!normal.equals(other.normal))
            return false;
        if (textureCoordinates == null) {
            if (other.textureCoordinates != null)
                return false;
        } else if (!textureCoordinates.equals(other.textureCoordinates))
            return false;
        if (pointIndex != other.pointIndex)
            return false;
        return true;
    }

}
