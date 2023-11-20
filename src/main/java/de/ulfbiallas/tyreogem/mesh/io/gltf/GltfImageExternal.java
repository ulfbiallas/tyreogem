package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfImageExternal extends GltfImage {

    private final String uri;

    public GltfImageExternal(String uri, String mimeType) {
        this.uri = uri;
        this.mimeType = mimeType;
    }

    public String getUri() {
        return uri;
    }

}
