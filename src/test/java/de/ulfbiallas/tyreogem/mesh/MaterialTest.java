package de.ulfbiallas.tyreogem.mesh;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;

public class MaterialTest {

    @Test
    public void test_getterAndSetter() {
        final Material material = createMaterial();
        Assert.assertEquals("test-material", material.getName());
        Assert.assertEquals(new Vec3d(0.7, 0.7, 0.7), material.getKa());
        Assert.assertEquals(new Vec3d(0.6, 0.6, 0.6), material.getKd());
        Assert.assertEquals(new Vec3d(0.5, 0.5, 0.5), material.getKs());
        Assert.assertEquals("map_Ka.png", material.getMap_Ka().getName());
        Assert.assertEquals("map_Kd.png", material.getMap_Kd().getName());
        Assert.assertEquals("map_d.png", material.getMap_d().getName());
        Assert.assertEquals(0.4, material.getShininess(), 0.00001);
    }

    @Test
    public void test_clone() {
        final Material material = createMaterial();
        final Material cloneMaterial = material.clone();

        Assert.assertEquals("test-material", cloneMaterial.getName());
        Assert.assertEquals(new Vec3d(0.7, 0.7, 0.7), cloneMaterial.getKa());
        Assert.assertEquals(new Vec3d(0.6, 0.6, 0.6), cloneMaterial.getKd());
        Assert.assertEquals(new Vec3d(0.5, 0.5, 0.5), cloneMaterial.getKs());
        Assert.assertEquals("map_Ka.png", cloneMaterial.getMap_Ka().getName());
        Assert.assertEquals("map_Kd.png", cloneMaterial.getMap_Kd().getName());
        Assert.assertEquals("map_d.png", cloneMaterial.getMap_d().getName());
        Assert.assertEquals(0.4, cloneMaterial.getShininess(), 0.00001);

        cloneMaterial.setName("test-material-2");
        cloneMaterial.setKa(new Vec3d(0.75, 0.7, 0.7));
        cloneMaterial.setKd(new Vec3d(0.65, 0.6, 0.6));
        cloneMaterial.setKs(new Vec3d(0.55, 0.5, 0.5));
        cloneMaterial.setMap_Ka(new File("map_Ka2.png"));
        cloneMaterial.setMap_Kd(new File("map_Kd2.png"));
        cloneMaterial.setMap_d(new File("map_d2.png"));
        cloneMaterial.setShininess(0.45);

        Assert.assertEquals("test-material", material.getName());
        Assert.assertEquals(new Vec3d(0.7, 0.7, 0.7), material.getKa());
        Assert.assertEquals(new Vec3d(0.6, 0.6, 0.6), material.getKd());
        Assert.assertEquals(new Vec3d(0.5, 0.5, 0.5), material.getKs());
        Assert.assertEquals("map_Ka.png", material.getMap_Ka().getName());
        Assert.assertEquals("map_Kd.png", material.getMap_Kd().getName());
        Assert.assertEquals("map_d.png", material.getMap_d().getName());
        Assert.assertEquals(0.4, material.getShininess(), 0.00001);
    }

    private static Material createMaterial() {
        final Material material = new Material();
        material.setName("test-material");
        material.setKa(new Vec3d(0.7, 0.7, 0.7));
        material.setKd(new Vec3d(0.6, 0.6, 0.6));
        material.setKs(new Vec3d(0.5, 0.5, 0.5));
        material.setMap_Ka(new File("map_Ka.png"));
        material.setMap_Kd(new File("map_Kd.png"));
        material.setMap_d(new File("map_d.png"));
        material.setShininess(0.4);
        return material;
    }
}
