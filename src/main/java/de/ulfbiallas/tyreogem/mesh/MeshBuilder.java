package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class MeshBuilder {

    public static Mesh createSphere(double radius, int vSegments, int hSegments) {
        return createSphere(radius, vSegments, hSegments, null);
    }

    public static Mesh createSphere(double radius, int vSegments, int hSegments, Material material) {
        int vn = vSegments < 3 ? 3 : vSegments;
        int hn = hSegments < 2 ? 2 : hSegments;

        final List<Vec3d> points = new ArrayList<>();
        points.add(new Vec3d(0, -radius, 0)); // bottom vertex
        for(int h=1; h<hn; ++h) {
            double theta = - 0.5 * Math.PI + Math.PI * h / hn;
            for(int k=0; k<vn; ++k) {
                points.add(new Vec3d(Math.cos(theta) * Math.cos(k * 2 * Math.PI / vn) * radius, Math.sin(theta) * radius, Math.cos(theta) * Math.sin(k * 2 * Math.PI / vn) * radius));
            }
        }
        points.add(new Vec3d(0, radius, 0)); // top vertex

        final List<Face> faces = new ArrayList<>();
        for(int h=0; h<hn; ++h) {
            for(int k=0; k<vn; ++k) {
                final int s0 = k;
                final int s1 = k < vn-1 ? k+1 : 0;
                if(h==0) {
                    faces.add(new Face(Arrays.asList(
                            new Vertex(1+s0, null, null),
                            new Vertex(1+s1, null, null),
                            new Vertex(0, null, null)
                    ), material));
                } else if(h==hn-1) {
                    faces.add(new Face(Arrays.asList(
                            new Vertex(points.size()-1, null, null),
                            new Vertex(1+(h-1)*vn+s1, null, null),
                            new Vertex(1+(h-1)*vn+s0, null, null)
                    ), material));
                } else {
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

        final Face bottom = new Face(Arrays.asList(  //
            new Vertex(0, new Vec3d(0, -1, 0), null), //
            new Vertex(1, new Vec3d(0, -1, 0), null), //
            new Vertex(2, new Vec3d(0, -1, 0), null), //
            new Vertex(3, new Vec3d(0, -1, 0), null) //
        ), material);

        final Face top = new Face(Arrays.asList( //
            new Vertex(7, new Vec3d(0, 1, 0), null), //
            new Vertex(6, new Vec3d(0, 1, 0), null), //
            new Vertex(5, new Vec3d(0, 1, 0), null), //
            new Vertex(4, new Vec3d(0, 1, 0), null) //
        ), material);

        final Face left = new Face(Arrays.asList( //
            new Vertex(4, new Vec3d(-1, 0, 0), null), //
            new Vertex(0, new Vec3d(-1, 0, 0), null), //
            new Vertex(3, new Vec3d(-1, 0, 0), null), //
            new Vertex(7, new Vec3d(-1, 0, 0), null) //
        ), material);

        final Face right = new Face(Arrays.asList( //
            new Vertex(6, new Vec3d(1, 0, 0), null), //
            new Vertex(2, new Vec3d(1, 0, 0), null), //
            new Vertex(1, new Vec3d(1, 0, 0), null), //
            new Vertex(5, new Vec3d(1, 0, 0), null) //
        ), material);

        final Face front = new Face(Arrays.asList( //
            new Vertex(4, new Vec3d(0, 0, -1), null), //
            new Vertex(5, new Vec3d(0, 0, -1), null), //
            new Vertex(1, new Vec3d(0, 0, -1), null), //
            new Vertex(0, new Vec3d(0, 0, -1), null) //
        ), material);

        final Face back = new Face(Arrays.asList( //
            new Vertex(6, new Vec3d(0, 0, 1), null), //
            new Vertex(7, new Vec3d(0, 0, 1), null), //
            new Vertex(3, new Vec3d(0, 0, 1), null), //
            new Vertex(2, new Vec3d(0, 0, 1), null) //
        ), material);

        final List<Face> faces = Arrays.asList(bottom, top, left, right, front, back);

        return new Mesh(points, faces);
    }

    public static Mesh createCylinder(double radius, double height, int segments) {
        return createCylinder(radius, height, segments, null);
    }

    public static Mesh createCylinder(double radius, double height, int segments, Material material) {
        final int n = segments < 3 ? 3 : segments;
        final double h = 0.5 * height;

        final List<Vec3d> points = new ArrayList<>();
        for(int k=0; k<segments; ++k) {
            points.add(new Vec3d(Math.cos(k * 2 * Math.PI / n) * radius, -h, Math.sin(k * 2 * Math.PI / n) * radius));
        }
        for(int k=0; k<segments; ++k) {
            points.add(new Vec3d(Math.cos(k * 2 * Math.PI / n) * radius, h, Math.sin(k * 2 * Math.PI / n) * radius));
        }

        final List<Face> faces = new ArrayList<>();
        for(int k=0; k<segments; ++k) {
            final int s0 = k;
            final int s1 = k < segments-1 ? k+1 : 0;
            faces.add(new Face(Arrays.asList(
                    new Vertex(s0+n, null, null),
                    new Vertex(s1+n, null, null),
                    new Vertex(s1, null, null),
                    new Vertex(s0, null, null)
            ), material));
        }

        faces.add(new Face(IntStream.range(0, n).boxed().map(i -> new Vertex(i, null, null)).collect(Collectors.toList()), material));
        faces.add(new Face(IntStream.range(n, 2*n).boxed().map(i -> new Vertex(i, null, null)).collect(Collectors.toList()), material).reverseWinding());

        return new Mesh(points, faces);
    }

}
