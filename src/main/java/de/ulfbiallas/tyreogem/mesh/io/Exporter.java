package de.ulfbiallas.tyreogem.mesh.io;

import java.io.File;

import de.ulfbiallas.tyreogem.mesh.Mesh;

public interface Exporter {

    void exportMesh(Mesh mesh, File file);

}
