package de.ulfbiallas.tyreogem.mesh;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class MeshBuilder {

    public static Mesh createCuboid(Vec3d position, Vec3d dimension) {
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
            new Vertex(3, new Vec3d(0, -1, 0), null), //
            new Vertex(2, new Vec3d(0, -1, 0), null), //
            new Vertex(1, new Vec3d(0, -1, 0), null), //
            new Vertex(0, new Vec3d(0, -1, 0), null) //
        ));

        final Face top = new Face(Arrays.asList( //
            new Vertex(4, new Vec3d(0, 1, 0), null), //
            new Vertex(5, new Vec3d(0, 1, 0), null), //
            new Vertex(6, new Vec3d(0, 1, 0), null), //
            new Vertex(7, new Vec3d(0, 1, 0), null) //
        ));

        final Face left = new Face(Arrays.asList( //
            new Vertex(7, new Vec3d(-1, 0, 0), null), //
            new Vertex(3, new Vec3d(-1, 0, 0), null), //
            new Vertex(0, new Vec3d(-1, 0, 0), null), //
            new Vertex(4, new Vec3d(-1, 0, 0), null) //
        ));

        final Face right = new Face(Arrays.asList( //
            new Vertex(5, new Vec3d(1, 0, 0), null), //
            new Vertex(1, new Vec3d(1, 0, 0), null), //
            new Vertex(2, new Vec3d(1, 0, 0), null), //
            new Vertex(6, new Vec3d(1, 0, 0), null) //
        ));

        final Face front = new Face(Arrays.asList( //
            new Vertex(0, new Vec3d(0, 0, -1), null), //
            new Vertex(1, new Vec3d(0, 0, -1), null), //
            new Vertex(5, new Vec3d(0, 0, -1), null), //
            new Vertex(4, new Vec3d(0, 0, -1), null) //
        ));

        final Face back = new Face(Arrays.asList( //
            new Vertex(2, new Vec3d(0, 0, 1), null), //
            new Vertex(3, new Vec3d(0, 0, 1), null), //
            new Vertex(7, new Vec3d(0, 0, 1), null), //
            new Vertex(6, new Vec3d(0, 0, 1), null) //
        ));

        final List<Face> faces = Arrays.asList(bottom, top, left, right, front, back);

        return new Mesh(points, faces);
    }

    public static Mesh createCylinder(double radius, double height, int segments) {
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
                    new Vertex(s0, null, null),
                    new Vertex(s1, null, null),
                    new Vertex(s1+n, null, null),
                    new Vertex(s0+n, null, null)
            )));
        }

        faces.add(new Face(IntStream.range(0, n).boxed().map(i -> new Vertex(i, null, null)).collect(Collectors.toList())).reverseWinding());
        faces.add(new Face(IntStream.range(n, 2*n).boxed().map(i -> new Vertex(i, null, null)).collect(Collectors.toList())));

        return new Mesh(points, faces);
    }

}
