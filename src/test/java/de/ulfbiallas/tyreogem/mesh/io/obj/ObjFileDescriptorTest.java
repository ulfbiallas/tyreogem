package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

public class ObjFileDescriptorTest {

    @Test
    public void test_constructor() {
        ObjFileDescriptor descriptor = new ObjFileDescriptor(new File("/some/path/to/a/model.mtl"));
        
        // TODO what about windows / unix ?
        //Assert.assertEquals("/some/path/to/a/", descriptor.getDirectoryPath());
        Assert.assertEquals("model", descriptor.getFileName());
    }

}
