package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.Face;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.Vertex;

public class ObjExporterTest {

    @Test
    public void test_writeObjFile() throws IOException {
        final ObjExporter exporter = new ObjExporter();
        final Mesh mesh = createMesh();
        final ObjMesh objMesh = new ObjMesh(mesh);

        final Writer writer = new StringWriter();
        exporter.writeObjFile(objMesh, writer);

        String expectedExport = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("obj/model-with-textureCoordinates-and-normals.obj"),"UTF-8");
        final String export = writer.toString();
        Assert.assertEquals(expectedExport, export);
    }

    @Test
    public void test_writeMtlFile() throws IOException {
        final ObjExporter exporter = new ObjExporter();
        final Mesh mesh = createMesh();
        final ObjMesh objMesh = new ObjMesh(mesh);

        final Writer writer = new StringWriter();
        exporter.writeMtlFile(objMesh, writer);

        String expectedExport = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("obj/model-with-textureCoordinates-and-normals.mtl"),"UTF-8");
        final String export = writer.toString();
        Assert.assertEquals(expectedExport, export);
    }

    private static Mesh createMesh() {
        final Material material = new Material();
        material.setName("test-material");
        material.setKa(new Vec3d(0.7, 0.7, 0.7));
        material.setKd(new Vec3d(0.6, 0.6, 0.6));
        material.setKs(new Vec3d(0.5, 0.5, 0.5));
        material.setMap_Ka(new File("map_Ka.png"));
        material.setMap_Kd(new File("map_Kd.png"));
        material.setMap_d(new File("map_d.png"));
        material.setShininess(0.4);
        final Vec3d v0 = new Vec3d(0.0, 0.0, 0.0);
        final Vec3d v1 = new Vec3d(1.0, 0.0, 0.0);
        final Vec3d v2 = new Vec3d(1.0, 0.0, 1.0);
        final Vec3d v3 = new Vec3d(0.0, 0.0, 1.0);
        final Vertex vertex0 = new Vertex(0, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 0.0));
        final Vertex vertex1 = new Vertex(1, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 0.0));
        final Vertex vertex2 = new Vertex(2, new Vec3d(0.0, 1.0, 0.0), new Vec2d(1.0, 1.0));
        final Vertex vertex3 = new Vertex(3, new Vec3d(0.0, 1.0, 0.0), new Vec2d(0.0, 1.0));
        final Face face0 = new Face(Arrays.asList(vertex0, vertex1, vertex2), material);
        final Face face1 = new Face(Arrays.asList(vertex2, vertex3, vertex0), material);
        final Mesh mesh = new Mesh(Arrays.asList(v0, v1, v2, v3), Arrays.asList(face0, face1));
        return mesh;
    }
}
