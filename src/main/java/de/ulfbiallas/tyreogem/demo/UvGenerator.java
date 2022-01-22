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

public class UvGenerator {

    public static void main(String[] args) {
        Exporter exporter = new ObjExporter();

        File file = new File("/Users/ubiallas/Desktop/meshes/uv/uvmap2.png");

        Material material = new Material();
        material.setName("uvmap");
        material.setMap_Kd(file);

        Mesh mesh = MeshBuilder.createCuboid(Vec3d.zero(), new Vec3d(5, 3, 4), material);
        //Mesh mesh = MeshBuilder.createCylinder(2, 5, 6, material).reverseWinding();
        //Mesh mesh = MeshBuilder.createSphere(10, 8, 8, material).reverseWinding();




        final List<UvGenFace> uvGenFaces = mesh.getFaces().stream().map(face -> new UvGenFace(face, mesh)).collect(Collectors.toList());

        final UvMap uvMap = new UvMap(uvGenFaces);
        uvMap.updateTextureCoordinates();


        uvMap.writeFile(file);

        exporter.exportMesh(mesh, new File("/Users/ubiallas/Desktop/meshes/uv"), "mesh");

        System.out.println("test");
    }
}
