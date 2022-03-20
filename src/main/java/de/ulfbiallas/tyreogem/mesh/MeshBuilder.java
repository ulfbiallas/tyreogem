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
        pointsOffsets.add(new Vec3d(0, -radius, 0)); // bottom vertex
        for(int h=1; h<hn; ++h) {
            double theta = - 0.5 * Math.PI + Math.PI * h / hn;
            for(int k=0; k<vn; ++k) {
                pointsOffsets.add(new Vec3d(Math.cos(theta) * Math.cos(k * 2 * Math.PI / vn) * radius, Math.sin(theta) * radius, Math.cos(theta) * Math.sin(k * 2 * Math.PI / vn) * radius));
            }
        }
        pointsOffsets.add(new Vec3d(0, radius, 0)); // top vertex

        final List<Vec3d> points = pointsOffsets.stream().map(offset -> position.add(offset)).collect(Collectors.toList());

        final List<Face> faces = new ArrayList<>();
        for(int h=0; h<hn; ++h) {
            for(int k=0; k<vn; ++k) {
                final int s0 = k;
                final int s1 = k < vn-1 ? k+1 : 0;
                if(h==0) {
                    // bottom triangles
                    faces.add(new Face(Arrays.asList(
                            new Vertex(1+s0, null, null),
                            new Vertex(1+s1, null, null),
                            new Vertex(0, null, null)
                    ), material));
                } else if(h==hn-1) {
                    // body quads
                    faces.add(new Face(Arrays.asList(
                            new Vertex(points.size()-1, null, null),
                            new Vertex(1+(h-1)*vn+s1, null, null),
                            new Vertex(1+(h-1)*vn+s0, null, null)
                    ), material));
                } else {
                    // top triangles
                    faces.add(new Face(Arrays.asList(
                            new Vertex(1+h*vn+s0, null, null),
                            new Vertex(1+h*vn+s1, null, null),
                            new Vertex(1+(h-1)*vn+s1, null, null),
                            new Vertex(1+(h-1)*vn+s0, null, null)
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
