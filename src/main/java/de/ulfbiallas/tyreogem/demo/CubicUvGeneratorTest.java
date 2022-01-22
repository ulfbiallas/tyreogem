package de.ulfbiallas.tyreogem.demo;

import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.demo.uvgen.UvGenFace;
import de.ulfbiallas.tyreogem.demo.uvgen.UvMap;
import de.ulfbiallas.tyreogem.mesh.Material;
import de.ulfbiallas.tyreogem.mesh.Mesh;
import de.ulfbiallas.tyreogem.mesh.MeshBuilder;
import de.ulfbiallas.tyreogem.mesh.io.Exporter;
import de.ulfbiallas.tyreogem.mesh.io.obj.ObjExporter;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class CubicUvGeneratorTest {

    public static void main(String[] args) {

        File texture = new File("/Users/ubiallas/Desktop/meshes/uvchecker.png");
        Material material = new Material();
        material.setName("uvchecker");
        material.setMap_Kd(texture);

        Exporter exporter = new ObjExporter();

        //Mesh mesh = MeshBuilder.createCuboid(Vec3d.zero(), new Vec3d(5, 3, 4), material);
        //Mesh mesh = MeshBuilder.createSphere(3, 8, 8, material);
        Mesh mesh = MeshBuilder.createCylinder(2, 5, 6, material);

        //CubicUvGenerator.generateCubicMapping(mesh, 0.5, 0.5, 0, 0);
        CubicUvGenerator.generateSphericalMapping(mesh);

        exporter.exportMesh(mesh, new File("/Users/ubiallas/Desktop/meshes/cubicuv"), "mesh");

    }
}
