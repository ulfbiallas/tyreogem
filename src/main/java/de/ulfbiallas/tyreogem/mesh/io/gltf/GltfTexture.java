package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfTexture {

    private final int sampler;

    private final int source;

    public GltfTexture(int sampler, int source) {
        this.sampler = sampler;
        this.source = source;
    }

    public int getSampler() {
        return sampler;
    }

    public int getSource() {
        return source;
    }

}
