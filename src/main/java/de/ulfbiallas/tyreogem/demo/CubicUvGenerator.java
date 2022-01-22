package de.ulfbiallas.tyreogem.demo;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.Vertex;

public class CubicUvGenerator {

    public static void generateCubicMapping(Mesh mesh) {
        generateCubicMapping(mesh, 1.0, 1.0, 0.0, 0.0);
    }

    // Source: https://en.wikipedia.org/wiki/Cube_mapping
    public static void generateCubicMapping(Mesh mesh, double uScale, double vScale, double uOffset, double vOffset) {

        mesh.getFaces().forEach(face -> {
            final Vec3d normal = mesh.getFaceNormal(face);

            double absX = Math.abs(normal.x);
            double absY = Math.abs(normal.y);
            double absZ = Math.abs(normal.z);

            boolean isXPositive = normal.x > 0;
            boolean isYPositive = normal.y > 0;
            boolean isZPositive = normal.z > 0;

            for (int k = 0; k < face.getVertices().size(); ++k) {
                Vertex vertex = face.getVertices().get(k);

                Vec3d p = mesh.getPoints().get(vertex.getPointIndex());

                double maxAxis = 0;
                double uc = 0;
                double vc = 0;

                if (isXPositive && absX >= absY && absX >= absZ) {
                    maxAxis = absX;
                    uc = -p.z;
                    vc = p.y;
                }

                if (!isXPositive && absX >= absY && absX >= absZ) {
                    maxAxis = absX;
                    uc = p.z;
                    vc = p.y;
                }

                if (isYPositive && absY >= absX && absY >= absZ) {
                    maxAxis = absY;
                    uc = p.x;
                    vc = -p.z;
                }

                if (!isYPositive && absY >= absX && absY >= absZ) {
                    maxAxis = absY;
                    uc = p.x;
                    vc = p.z;
                }

                if (isZPositive && absZ >= absX && absZ >= absY) {
                    maxAxis = absZ;
                    uc = p.x;
                    vc = p.y;
                }

                if (!isZPositive && absZ >= absX && absZ >= absY) {
                    maxAxis = absZ;
                    uc = -p.x;
                    vc = p.y;
                }

                double u = uScale * ((uc / maxAxis + 1.0)) + uOffset;
                double v = vScale * ((vc / maxAxis + 1.0)) + uOffset;

                Vertex updatedVertex = new Vertex(vertex.getPointIndex(), vertex.getNormal(), new Vec2d(u, v));
                face.getVertices().set(k, updatedVertex);
            }
        });
    }

    public static void generateSphericalMapping(Mesh mesh) {
        generateSphericalMapping(mesh, 1.0, 1.0, 0.0, 0.0);
    }

    public static void generateSphericalMapping(Mesh mesh, double uScale, double vScale, double uOffset, double vOffset) {
        mesh.getFaces().forEach(face -> {
            final Vec3d normal = mesh.getFaceNormal(face);

            for (int k = 0; k < face.getVertices().size(); ++k) {
                Vertex vertex = face.getVertices().get(k);

                Vec3d p = mesh.getPoints().get(vertex.getPointIndex()).normalize();

                double u = 0.5 * (1.0 + Math.atan2(p.z, p.x) / Math.PI);
                double v = 0.5f + Math.asin(p.y) / Math.PI;

                Vertex updatedVertex = new Vertex(vertex.getPointIndex(), vertex.getNormal(), new Vec2d(u, v));
                face.getVertices().set(k, updatedVertex);
            }
        });
    }

}