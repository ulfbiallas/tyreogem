package de.ulfbiallas.tyreogem.mesh.obj;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.io.obj.ObjMaterial;

public class ObjMaterialTest {

    @Test
    public void test_constructor() {
        final Material material = new Material();
        material.setName("test-material");
        material.setKa(new Vec3d(2.0, 3.5, -1.3));
        material.setKd(new Vec3d(-3.1, 2.0, -0.8));
        material.setKs(new Vec3d(-1.5, 0.0, 2.6));
        material.setShininess(0.33);
        material.setMap_Ka(new File("map_Ka.png"));
        material.setMap_Kd(new File("map_Kd.png"));
        material.setMap_d(new File("map_d.png"));

        final ObjMaterial objMaterial = new ObjMaterial(material);

        Assert.assertEquals(new Vec3d(2.0, 3.5, -1.3), objMaterial.getKa());
        Assert.assertEquals(new Vec3d(-3.1, 2.0, -0.8), objMaterial.getKd());
        Assert.assertEquals(new Vec3d(-1.5, 0.0, 2.6), objMaterial.getKs());
        Assert.assertEquals(0.33, objMaterial.getSharpness(), 0.00001);
        Assert.assertTrue(objMaterial.getMap_Ka().endsWith("map_Ka.png"));
        Assert.assertTrue(objMaterial.getMap_Kd().endsWith("map_Kd.png"));
        Assert.assertTrue(objMaterial.getMap_d().endsWith("map_d.png"));
    }

    @Test
    public void test_toMaterial() {
        final ObjMaterial objMaterial = new ObjMaterial();
        objMaterial.setName("test-material");
        objMaterial.setKa(new Vec3d(2.0, 3.5, -1.3));
        objMaterial.setKd(new Vec3d(-3.1, 2.0, -0.8));
        objMaterial.setKs(new Vec3d(-1.5, 0.0, 2.6));
        objMaterial.setSharpness(0.33);
        objMaterial.setMap_Ka("map_Ka.png");
        objMaterial.setMap_Kd("map_Kd.png");
        objMaterial.setMap_d("map_d.png");

        final Material material = objMaterial.toMaterial();

        Assert.assertEquals(new Vec3d(2.0, 3.5, -1.3), material.getKa());
        Assert.assertEquals(new Vec3d(-3.1, 2.0, -0.8), material.getKd());
        Assert.assertEquals(new Vec3d(-1.5, 0.0, 2.6), material.getKs());
        Assert.assertEquals(0.33, material.getShininess(), 0.00001);
        Assert.assertTrue(material.getMap_Ka().getAbsolutePath().endsWith("map_Ka.png"));
        Assert.assertTrue(material.getMap_Kd().getAbsolutePath().endsWith("map_Kd.png"));
        Assert.assertTrue(material.getMap_d().getAbsolutePath().endsWith("map_d.png"));
    }
}
