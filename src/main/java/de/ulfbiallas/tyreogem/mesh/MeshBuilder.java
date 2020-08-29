package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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

}
