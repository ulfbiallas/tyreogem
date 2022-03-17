package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.util.List;

public class GltfScene {

    private final List<Integer> nodes;

    public GltfScene(List<Integer> nodes) {
        this.nodes = nodes;
    }

    public List<Integer> getNodes() {
        return nodes;
    }

}
