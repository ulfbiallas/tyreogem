package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.util.List;

public class GltfMesh {

    final private String name;

    final private List<GltfPrimitive> primitives;

    public GltfMesh(String name, List<GltfPrimitive> primitives) {
        this.name = name;
        this.primitives = primitives;
    }

    public String getName() {
        return name;
    }

    public List<GltfPrimitive> getPrimitives() {
        return primitives;
    }

}
