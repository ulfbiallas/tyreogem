package de.ulfbiallas.tyreogem.mesh.io;

import java.io.File;

import de.ulfbiallas.tyreogem.mesh.Mesh;

public interface Importer {

    Mesh importMesh(File file);

}
