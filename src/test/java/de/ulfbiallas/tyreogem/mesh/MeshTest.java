package de.ulfbiallas.tyreogem.mesh;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;

public class MeshTest {

    @Test
    public void test_constructor() {
        Vec3d v0 = new Vec3d(0.0, 0.0, 0.0);
        Vec3d v1 = new Vec3d(1.0, 0.0, 0.0);
        Vec3d v2 = new Vec3d(1.0, 0.0, 1.0);
        Vec3d v3 = new Vec3d(0.0, 0.0, 1.0);
        FaceVertex faceVertex0 = new FaceVertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 0.0));
        FaceVertex faceVertex1 = new FaceVertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 0.0));
        FaceVertex faceVertex2 = new FaceVertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 1.0));
        FaceVertex faceVertex3 = new FaceVertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 1.0));
        Face face0 = new Face(Arrays.asList(faceVertex0, faceVertex1, faceVertex2));
        Face face1 = new Face(Arrays.asList(faceVertex2, faceVertex3, faceVertex0));

        Mesh mesh = new Mesh(Arrays.asList(v0, v1, v2, v3), Arrays.asList(face0, face1));

        Assert.assertEquals(4, mesh.getVertices().size());
        Assert.assertEquals(v0, mesh.getVertices().get(0));
        Assert.assertEquals(v1, mesh.getVertices().get(1));
        Assert.assertEquals(v2, mesh.getVertices().get(2));
        Assert.assertEquals(v3, mesh.getVertices().get(3));

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getVertexIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(0.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getVertexIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(1.0, 1.0), mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());
    }

}
