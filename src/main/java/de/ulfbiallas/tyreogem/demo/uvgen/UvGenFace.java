package de.ulfbiallas.tyreogem.demo.uvgen;

import de.ulfbiallas.tyreogem.core.math.Matrix2x2d;
import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.core.spatial.Ray;
import de.ulfbiallas.tyreogem.mesh.Face;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.Vertex;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UvGenFace {

    private final Face faceReference;

    private final Vec3d center;

    private final Vec3d normal;

    private final Vec2d centerUv;

    private final Vec3d uAxis;

    private final Vec3d vAxis;

    private final List<Vec2d> uv;

    private final List<Vec2d> uvAabb;

    private Vec2d uvOffset = Vec2d.zero();

    public UvGenFace(Face face, Mesh mesh) {

        this.faceReference = face;
        this.uv = new ArrayList<>();

        final List<Vec3d> vertexPositions = face.getVertices().stream().map(v -> mesh.getPoints().get(v.getPointIndex())).collect(Collectors.toList());

        // we assume the face is planar
        this.center = Vec3d.arithmeticMean(vertexPositions);
        this.normal = mesh.getFaceNormal(face);

        final Vec3d baseDir = Math.abs(normal.dot(Vec3d.e2())) < Math.abs(normal.dot(Vec3d.e3())) ? Vec3d.e2() : Vec3d.e3();
        this.uAxis = baseDir.cross(normal);
        this.vAxis = uAxis.cross(normal);

        for(Vec3d p: vertexPositions) {
            uv.add(getUv(p));
        }
        double uMin = uv.stream().map(uv -> uv.x).min(Double::compare).get();
        double vMin = uv.stream().map(uv -> uv.y).min(Double::compare).get();

        for(int k=0; k<uv.size(); ++k) {
            uv.set(k, uv.get(k).sub(new Vec2d(uMin, vMin)));
        }
        this.centerUv = new Vec2d(uMin, vMin).negate();

        this.uvAabb = new ArrayList<>();
        double uvAabbMinX = uv.stream().map(uv -> uv.x).min(Double::compare).get();
        double uvAabbMinY = uv.stream().map(uv -> uv.y).min(Double::compare).get();
        double uvAabbMaxX = uv.stream().map(uv -> uv.x).max(Double::compare).get();
        double uvAabbMaxY = uv.stream().map(uv -> uv.y).max(Double::compare).get();
        this.uvAabb.add(new Vec2d(uvAabbMinX, uvAabbMinY));
        this.uvAabb.add(new Vec2d(uvAabbMaxX, uvAabbMaxY));
    }

    public void updateTextureCoordinates(int width, int height, int lumelsPerWorldUnit) {
        for(int k=0; k<this.faceReference.getVertices().size(); ++k) {
            Vec2d uv = this.uvOffset.add(this.uv.get(k).scale(lumelsPerWorldUnit));
            Vec2d uvNormalized = new Vec2d(uv.x / width, 1.0 - uv.y / height);
            Vertex vertex = this.faceReference.getVertices().get(k);
            Vertex updatedVertex = new Vertex(vertex.getPointIndex(), vertex.getNormal(), uvNormalized);
            this.faceReference.getVertices().set(k, updatedVertex);
        }
    }

    private Vec2d getUv(Vec3d point) {
        final Vec3d r1 = this.uAxis;
        final Vec3d r2 = this.vAxis;
        final Vec3d pa = point.sub(this.center);

        final double ma = r1.dot(r1);
        final double mb = r1.dot(r2);
        final double mc = r2.dot(r1);
        final double md = r2.dot(r2);
        final double me = r1.dot(pa);
        final double mf = r2.dot(pa);

        final Matrix2x2d matrix = new Matrix2x2d(ma, mb, mc, md);
        final Vec2d result = matrix.solve(new Vec2d(me, mf));

        final double u = result.x;
        final double v = result.y;

        return new Vec2d(u, v);
    }

    public Vec2d getSize() {
        return this.uvAabb.get(1); // assuming the first is (0, 0).
    }

    public void setUvOffset(Vec2d uvOffset) {
        this.uvOffset = uvOffset;
    }

    public void draw(Graphics2D graphics2d, int lumelsPerWorldUnit) {
        final List<Vec2d> points = this.uv.stream().map(p -> uvOffset.add(p.scale(lumelsPerWorldUnit))).collect(Collectors.toList());
        final int xPoints[] = points.stream().map(p -> (int) p.x).mapToInt(i->i).toArray();
        final int yPoints[] = points.stream().map(p -> (int) p.y).mapToInt(i->i).toArray();
        final Shape polygon = new Polygon(xPoints, yPoints, this.uv.size());

        final Rectangle2D aabb = polygon.getBounds2D();
        for(int iy = (int) Math.floor(aabb.getMinY()); iy<Math.ceil(aabb.getMaxY()); ++iy) {
            for(int ix = (int) Math.floor(aabb.getMinX()); ix<Math.ceil(aabb.getMaxX()); ++ix) {
                if(polygon.contains(ix, iy)) {
                    final Vec2d uv = new Vec2d(ix, iy).sub(uvOffset).scale(1.0 / lumelsPerWorldUnit);
                    final Vec3d lumelPosition = getLumelPosition(uv);

                    final Vec3d visualizedLumelPosition = lumelPosition.normalize();
                    graphics2d.setColor(new Color((float) Math.abs(visualizedLumelPosition.x), Math.abs((float) visualizedLumelPosition.y), (float) Math.abs(visualizedLumelPosition.z)));

                    final Ray initialRay = new Ray(lumelPosition, this.normal); // improvement: interpolate vertex normals
                    // TODO: calculate light using ray tracing

                    //graphics2d.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
                    graphics2d.fillRect(ix, iy, 1, 1);
                }
            }
        }

        //graphics2d.fill(polygon);
    }

    public Vec3d getLumelPosition(Vec2d uv) {
        Vec2d uvRelativeToCenter = uv.sub(centerUv);
        Vec3d lumelPosition = center.add(this.uAxis.scale(uvRelativeToCenter.x)).add(this.vAxis.scale(uvRelativeToCenter.y));
        return lumelPosition;
    }
}
