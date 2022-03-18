package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfBuffer {

    final private String uri;

    final private int byteLength;

    public GltfBuffer(String uri, int byteLength) {
        this.uri = uri;
        this.byteLength = byteLength;
    }

    public String getUri() {
        return uri;
    }

    public int getByteLength() {
        return byteLength;
    }

}
