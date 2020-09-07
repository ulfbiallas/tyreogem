package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class ObjFaceTest {

    @Test
    public void test_constructor() {
        ObjFace face = new ObjFace(Arrays.asList(new ObjFaceIndex(1, 4, 2)), "test-material");
        Assert.assertEquals(new ObjFaceIndex(1, 4, 2), face.getIndices().get(0));
        Assert.assertEquals("test-material", face.getMaterialName());
    }

    @Test
    public void test_constructor2() {
        ObjFace face = new ObjFace(Arrays.asList(new ObjFaceIndex(1, 4, 2)));
        Assert.assertEquals(new ObjFaceIndex(1, 4, 2), face.getIndices().get(0));
        Assert.assertEquals(ObjMaterial.NO_MATERIAL, face.getMaterialName());
    }

}
