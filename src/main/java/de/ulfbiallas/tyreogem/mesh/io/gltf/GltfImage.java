package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfImage {

    private final int bufferView;

    private final String mimeType;

    public GltfImage(int bufferView, String mimeType) {
        this.bufferView = bufferView;
        this.mimeType = mimeType;
    }

    public int getBufferView() {
        return bufferView;
    }

    public String getMimeType() {
        return mimeType;
    }

}
