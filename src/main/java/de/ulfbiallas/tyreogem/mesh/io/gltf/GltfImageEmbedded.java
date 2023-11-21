package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfImageEmbedded extends GltfImage {

    private final int bufferView;

    public GltfImageEmbedded(int bufferView, String mimeType) {
        this.bufferView = bufferView;
        this.mimeType = mimeType;
    }

    public int getBufferView() {
        return bufferView;
    }

}
