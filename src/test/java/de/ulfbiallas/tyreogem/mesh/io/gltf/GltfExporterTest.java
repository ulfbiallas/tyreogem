package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.net.URISyntaxException;
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

public class GltfExporterTest {

    @Test
    public void test_exportMesh_withMaterial() throws IOException, URISyntaxException {
        final GltfExporter exporter = new GltfExporter();
        final Material material = createMaterial();
        final Mesh mesh = createMesh(material);

        final Writer writer = new StringWriter();
        exporter.exportMesh(mesh, writer);

        String expectedExport = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("gltf/model-with-materials.gltf"),"UTF-8");
        final String export = writer.toString();
        Assert.assertEquals(expectedExport, export);
    }

    @Test
    public void test_exportMesh_withoutMaterial() throws IOException, URISyntaxException {
        final GltfExporter exporter = new GltfExporter();
        final Material material = null;
        final Mesh mesh = createMesh(material);

        final Writer writer = new StringWriter();
        exporter.exportMesh(mesh, writer);

        String expectedExport = IOUtils.toString(this.getClass().getClassLoader().getResourceAsStream("gltf/model-without-materials.gltf"),"UTF-8");
        final String export = writer.toString();
        Assert.assertEquals(expectedExport, export);
    }

    private Material createMaterial() throws URISyntaxException {
        File texture = new File(this.getClass().getClassLoader().getResource("gltf/texture.png").toURI());
        final Material material = new Material();
        material.setName("test-material");
        material.setKa(new Vec3d(0.7, 0.7, 0.7));
        material.setKd(new Vec3d(0.6, 0.6, 0.6));
        material.setKs(new Vec3d(0.5, 0.5, 0.5));
        material.setMap_Ka(texture);
        material.setMap_Kd(texture);
        material.setMap_d(texture);
        material.setShininess(0.4);
        return material;
    }

    private Mesh createMesh(Material material) {
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
