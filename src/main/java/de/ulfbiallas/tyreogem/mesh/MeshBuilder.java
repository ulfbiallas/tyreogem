package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class MeshBuilder {

    public static Mesh createSphere(Vec3d position, double radius, int vSegments, int hSegments) {
        return createSphere(position, radius, vSegments, hSegments, null);
    }

    public static Mesh createSphere(Vec3d position, double radius, int vSegments, int hSegments, Material material) {
        int vn = vSegments < 3 ? 3 : vSegments;
        int hn = hSegments < 2 ? 2 : hSegments;

        final List<Vec3d> pointsOffsets = new ArrayList<>();
        final List<Vec2d> uvs = new ArrayList<>();
        pointsOffsets.add(new Vec3d(0, -radius, 0)); // bottom vertex
        uvs.add(new Vec2d(0, 0));
        for(int h=1; h<hn; ++h) {
            double theta = -0.5 * Math.PI + Math.PI * h / hn;
            for(int k=0; k<vn; ++k) {
                double phi = k * 2 * Math.PI / vn;
                pointsOffsets.add(new Vec3d(Math.cos(theta) * Math.cos(phi) * radius, Math.sin(theta) * radius, Math.cos(theta) * Math.sin(phi) * radius));
                uvs.add(new Vec2d(1.0 * k / vn, 1.0 * h / hn));
            }
        }
        pointsOffsets.add(new Vec3d(0, radius, 0)); // top vertex
        uvs.add(new Vec2d(0, 1.0));

        final List<Vec3d> points = pointsOffsets.stream().map(offset -> position.add(offset)).collect(Collectors.toList());

        final List<Face> faces = new ArrayList<>();
        for(int h=0; h<hn; ++h) {
            for(int k=0; k<vn; ++k) {
                final int s0 = k;
                final int s1 = k < vn-1 ? k+1 : 0;
                Vec3d n0, n1, n2, n3;
                Vec2d uv0, uv1, uv2, uv3;
                if(h==0) {
                    // bottom triangles
                    n0 = pointsOffsets.get(1+s0).normalize();
                    n1 = pointsOffsets.get(1+s1).normalize();
                    n2 = pointsOffsets.get(0).normalize();
                    uv0 = uvs.get(1+s0);
                    uv1 = new Vec2d(uv0.x + 1.0 / vn, uv0.y);
                    uv2 = new Vec2d(0.5 * (uv0.x + uv1.x), 0);
                    faces.add(new Face(Arrays.asList(
                            new Vertex(1+s0, n0, uv0),
                            new Vertex(1+s1, n1, uv1),
                            new Vertex(0,    n2, uv2)
                    ), material));
                } else if(h==hn-1) {
                    // top triangles
                    n0 = pointsOffsets.get(points.size()-1).normalize();
                    n1 = pointsOffsets.get(1+(h-1)*vn+s1).normalize();
                    n2 = pointsOffsets.get(1+(h-1)*vn+s0).normalize();
                    uv2 = uvs.get(1+(h-1)*vn+s0);
                    uv1 = new Vec2d(uv2.x + 1.0 / vn, uv2.y);
                    uv0 = new Vec2d(0.5 * (uv2.x + uv1.x), 1.0);
                    faces.add(new Face(Arrays.asList(
                            new Vertex(points.size()-1, n0, uv0),
                            new Vertex(1+(h-1)*vn+s1,   n1, uv1),
                            new Vertex(1+(h-1)*vn+s0,   n2, uv2)
                    ), material));
                } else {
                    // body quads
                    n0 = pointsOffsets.get(1+h*vn+s0).normalize();
                    n1 = pointsOffsets.get(1+h*vn+s1).normalize();
                    n2 = pointsOffsets.get(1+(h-1)*vn+s1).normalize();
                    n3 = pointsOffsets.get(1+(h-1)*vn+s0).normalize();
                    uv0 = uvs.get(1+h*vn+s0);
                    uv1 = new Vec2d(uv0.x + 1.0 / vn, uv0.y);
                    uv3 = uvs.get(1+(h-1)*vn+s0);
                    uv2 = new Vec2d(uv3.x + 1.0 / vn, uv3.y);
                    faces.add(new Face(Arrays.asList(
                            new Vertex(1+h*vn+s0,     n0, uv0),
                            new Vertex(1+h*vn+s1,     n1, uv1),
                            new Vertex(1+(h-1)*vn+s1, n2, uv2),
                            new Vertex(1+(h-1)*vn+s0, n3, uv3)
                    ), material));
                }
            }
        }

        return new Mesh(points, faces);
    }

    public static Mesh createCuboid(Vec3d position, Vec3d dimension) {
        return createCuboid(position, dimension, null);
    }

    public static Mesh createCuboid(Vec3d position, Vec3d dimension, Material material) {
        final Vec3d r = dimension.scale(0.5);
        final List<Vec3d> offsets = Arrays.asList( //
                new Vec3d(-r.x, -r.y, -r.z), //
                new Vec3d(r.x, -r.y, -r.z), //
                new Vec3d(r.x, -r.y, r.z), //
                new Vec3d(-r.x, -r.y, r.z), //
                new Vec3d(-r.x, r.y, -r.z), //
                new Vec3d(r.x, r.y, -r.z), //
                new Vec3d(r.x, r.y, r.z), //
                new Vec3d(-r.x, r.y, r.z) //
        );
        final List<Vec3d> points = offsets.stream().map(o -> o.add(position)).collect(Collectors.toList());

        double px = position.x;
        double py = position.y;
        double pz = position.z;
        double dx = dimension.x;
        double dy = dimension.y;
        double dz = dimension.z;

        final Face bottom = new Face(Arrays.asList(  //
            new Vertex(0, Vec3d.e2().negate(), new Vec2d(px,    pz+dz)), //
            new Vertex(1, Vec3d.e2().negate(), new Vec2d(px+dx, pz+dz)), //
            new Vertex(2, Vec3d.e2().negate(), new Vec2d(px+dx, pz   )), //
            new Vertex(3, Vec3d.e2().negate(), new Vec2d(px,    pz   )) //
        ), material);

        final Face top = new Face(Arrays.asList( //
            new Vertex(4, Vec3d.e2(), new Vec2d(px,    pz+dz)), //
            new Vertex(7, Vec3d.e2(), new Vec2d(px,    pz   )), //
            new Vertex(6, Vec3d.e2(), new Vec2d(px+dx, pz   )), //
            new Vertex(5, Vec3d.e2(), new Vec2d(px+dx, pz+dz)) //
        ), material);

        final Face left = new Face(Arrays.asList( //
            new Vertex(4, Vec3d.e1().negate(), new Vec2d(pz+dz, py+dy)), //
            new Vertex(0, Vec3d.e1().negate(), new Vec2d(pz+dz, py   )), //
            new Vertex(3, Vec3d.e1().negate(), new Vec2d(pz,    py   )), //
            new Vertex(7, Vec3d.e1().negate(), new Vec2d(pz,    py+dy)) //
        ), material);

        final Face right = new Face(Arrays.asList( //
            new Vertex(6, Vec3d.e1(), new Vec2d(pz,    py+dy)), //
            new Vertex(2, Vec3d.e1(), new Vec2d(pz,    py   )), //
            new Vertex(1, Vec3d.e1(), new Vec2d(pz+dz, py   )), //
            new Vertex(5, Vec3d.e1(), new Vec2d(pz+dz, py+dy)) //
        ), material);

        final Face front = new Face(Arrays.asList( //
            new Vertex(4, Vec3d.e3().negate(), new Vec2d(px   , py+dy)), //
            new Vertex(5, Vec3d.e3().negate(), new Vec2d(px+dx, py+dy)), //
            new Vertex(1, Vec3d.e3().negate(), new Vec2d(px+dx, py   )), //
            new Vertex(0, Vec3d.e3().negate(), new Vec2d(px   , py   )) //
        ), material);

        final Face back = new Face(Arrays.asList( //
            new Vertex(6, Vec3d.e3(), new Vec2d(px+dx, py+dy)), //
            new Vertex(7, Vec3d.e3(), new Vec2d(px,    py+dy)), //
            new Vertex(3, Vec3d.e3(), new Vec2d(px   , py   )), //
            new Vertex(2, Vec3d.e3(), new Vec2d(px+dx, py   )) //
        ), material);

        final List<Face> faces = Arrays.asList(bottom, top, left, right, front, back);

        return new Mesh(points, faces);
    }

    public static Mesh createCylinder(Vec3d position, double radius, double height, int segments) {
        return createCylinder(position, radius, height, segments, null);
    }

    public static Mesh createCylinder(Vec3d position, double radius, double height, int segments, Material material) {
        final int n = segments < 3 ? 3 : segments;
        final double h = 0.5 * height;
        final double segmentWidth = 2 * radius * Math.PI / segments;

        final List<Vec3d> pointsOffsets = new ArrayList<>();
        for(int k=0; k<segments; ++k) {
            pointsOffsets.add(new Vec3d(Math.cos(k * 2 * Math.PI / n) * radius, -h, Math.sin(k * 2 * Math.PI / n) * radius));
        }
        for(int k=0; k<segments; ++k) {
            pointsOffsets.add(new Vec3d(Math.cos(k * 2 * Math.PI / n) * radius, h, Math.sin(k * 2 * Math.PI / n) * radius));
        }

        final List<Vec3d> points = pointsOffsets.stream().map(offset -> position.add(offset)).collect(Collectors.toList());

        final List<Face> faces = new ArrayList<>();
        for(int k=0; k<segments; ++k) {
            final int s0 = k;
            final int s1 = k < segments-1 ? k+1 : 0;
            final Vec3d n0 = new Vec3d(Math.cos((k+0) * 2 * Math.PI / n), 0, Math.sin((k+0) * 2 * Math.PI / n));
            final Vec3d n1 = new Vec3d(Math.cos((k+1) * 2 * Math.PI / n), 0, Math.sin((k+1) * 2 * Math.PI / n));
            faces.add(new Face(Arrays.asList(
                    new Vertex(s0+n, n0, new Vec2d((k+0)* segmentWidth, h)),
                    new Vertex(s1+n, n1, new Vec2d((k+1)* segmentWidth, h)),
                    new Vertex(s1,   n1, new Vec2d((k+1)* segmentWidth, -h)),
                    new Vertex(s0,   n0, new Vec2d((k+0)* segmentWidth, -h))
            ), material));
        }

        faces.add(new Face(IntStream.range(0, n).boxed().map(i -> new Vertex(i, Vec3d.e2().negate(), new Vec2d(pointsOffsets.get(i).x, pointsOffsets.get(i).z))).collect(Collectors.toList()), material));
        faces.add(new Face(IntStream.range(n, 2*n).boxed().map(i -> new Vertex(i, Vec3d.e2(), new Vec2d(pointsOffsets.get(i).x, pointsOffsets.get(i).z))).collect(Collectors.toList()), material).reverseWinding());

        return new Mesh(points, faces);
    }

}
