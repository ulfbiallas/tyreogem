package de.ulfbiallas.tyreogem.mesh.io.obj;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.MeshBuilder;

public class ObjMeshTest {

    @Test
    public void test_constructor() {
        final Mesh mesh = MeshBuilder.createCuboid(new Vec3d(5, 2, -3), new Vec3d(4, 2, 3));

        final ObjMesh objMesh = new ObjMesh(mesh);

        Assert.assertEquals(8, objMesh.getVertices().size());
        Assert.assertEquals(new Vec3d(3, 1, -4.5), objMesh.getVertices().get(0));

        Assert.assertTrue(objMesh.getMaterials().isEmpty());

        Assert.assertEquals(4*6, objMesh.getVertexNormals().size());
        Assert.assertEquals(new Vec3d(0, -1, 0), objMesh.getVertexNormals().get(0));

        Assert.assertTrue(objMesh.getTextureCoordinates().isEmpty());

        Assert.assertEquals(6, objMesh.getFaces().size());
        Assert.assertEquals(4, objMesh.getFaces().get(0).getIndices().size());
        Assert.assertEquals(3, objMesh.getFaces().get(0).getIndices().get(0).getVertexIndex().intValue());
        Assert.assertEquals(2, objMesh.getFaces().get(0).getIndices().get(1).getVertexIndex().intValue());
        Assert.assertEquals(1, objMesh.getFaces().get(0).getIndices().get(2).getVertexIndex().intValue());
        Assert.assertEquals(0, objMesh.getFaces().get(0).getIndices().get(3).getVertexIndex().intValue());
    }

    
}
