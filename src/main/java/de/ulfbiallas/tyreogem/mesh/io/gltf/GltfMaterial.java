package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfMaterial {

    private final GltfPbrMetallicRoughness pbrMetallicRoughness;

    public GltfMaterial(GltfPbrMetallicRoughness pbrMetallicRoughness) {
        this.pbrMetallicRoughness = pbrMetallicRoughness;
    }

    public GltfPbrMetallicRoughness getPbrMetallicRoughness() {
        return pbrMetallicRoughness;
    }

}
