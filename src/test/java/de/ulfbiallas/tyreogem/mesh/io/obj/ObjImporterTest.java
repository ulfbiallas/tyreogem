package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.Mesh;

public class ObjImporterTest {

    @Test
    public void test_importMesh() {
        final ObjImporter importer = new ObjImporter();
        final Mesh mesh = importer.importMesh(new File("src/test/resources"), "model");

        Assert.assertEquals(4, mesh.getPoints().size());
        Assert.assertEquals(new Vec3d(0.0, 0.0, 0.0), mesh.getPoints().get(0));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 0.0), mesh.getPoints().get(1));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 1.0), mesh.getPoints().get(2));
        Assert.assertEquals(new Vec3d(0.0, 0.0, 1.0), mesh.getPoints().get(3));

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(null, mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(null, mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(null, mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(null, mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());

        final Material material = mesh.getFaces().get(0).getMaterial();
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
    public void test_importMesh_withTextureCoordinates() {
        final ObjImporter importer = new ObjImporter();
        final Mesh mesh = importer.importMesh(new File("src/test/resources"), "model-with-textureCoordinates");

        Assert.assertEquals(4, mesh.getPoints().size());
        Assert.assertEquals(new Vec3d(0.0, 0.0, 0.0), mesh.getPoints().get(0));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 0.0), mesh.getPoints().get(1));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 1.0), mesh.getPoints().get(2));
        Assert.assertEquals(new Vec3d(0.0, 0.0, 1.0), mesh.getPoints().get(3));

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(null, mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(0.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(null, mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(1.0, 1.0), mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());

        final Material material = mesh.getFaces().get(0).getMaterial();
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
    public void test_importMesh_withNormals() {
        final ObjImporter importer = new ObjImporter();
        final Mesh mesh = importer.importMesh(new File("src/test/resources"), "model-with-normals");

        Assert.assertEquals(4, mesh.getPoints().size());
        Assert.assertEquals(new Vec3d(0.0, 0.0, 0.0), mesh.getPoints().get(0));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 0.0), mesh.getPoints().get(1));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 1.0), mesh.getPoints().get(2));
        Assert.assertEquals(new Vec3d(0.0, 0.0, 1.0), mesh.getPoints().get(3));

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(null, mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(null, mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());

        final Material material = mesh.getFaces().get(0).getMaterial();
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
    public void test_importMesh_withTextureCoordinatesAndNormals() {
        final ObjImporter importer = new ObjImporter();
        final Mesh mesh = importer.importMesh(new File("src/test/resources"), "model-with-textureCoordinates-and-normals");

        Assert.assertEquals(4, mesh.getPoints().size());
        Assert.assertEquals(new Vec3d(0.0, 0.0, 0.0), mesh.getPoints().get(0));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 0.0), mesh.getPoints().get(1));
        Assert.assertEquals(new Vec3d(1.0, 0.0, 1.0), mesh.getPoints().get(2));
        Assert.assertEquals(new Vec3d(0.0, 0.0, 1.0), mesh.getPoints().get(3));

        Assert.assertEquals(2, mesh.getFaces().size());
        Assert.assertEquals(3, mesh.getFaces().get(0).getVertices().size());
        Assert.assertEquals(3, mesh.getFaces().get(1).getVertices().size());

        Assert.assertEquals(0, mesh.getFaces().get(0).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(0.0, 0.0), mesh.getFaces().get(0).getVertices().get(0).getTextureCoordinates());

        Assert.assertEquals(2, mesh.getFaces().get(1).getVertices().get(0).getPointIndex());
        Assert.assertEquals(new Vec3d(0.0, 1.0, 0.0), mesh.getFaces().get(1).getVertices().get(0).getNormal());
        Assert.assertEquals(new Vec2d(1.0, 1.0), mesh.getFaces().get(1).getVertices().get(0).getTextureCoordinates());

        final Material material = mesh.getFaces().get(0).getMaterial();
        Assert.assertEquals("test-material", material.getName());
        Assert.assertEquals(new Vec3d(0.7, 0.7, 0.7), material.getKa());
        Assert.assertEquals(new Vec3d(0.6, 0.6, 0.6), material.getKd());
        Assert.assertEquals(new Vec3d(0.5, 0.5, 0.5), material.getKs());
        Assert.assertEquals("map_Ka.png", material.getMap_Ka().getName());
        Assert.assertEquals("map_Kd.png", material.getMap_Kd().getName());
        Assert.assertEquals("map_d.png", material.getMap_d().getName());
        Assert.assertEquals(0.4, material.getShininess(), 0.00001);
    }
}
