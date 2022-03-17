package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfAsset {

    private final String version;

    private final String generator;

    public GltfAsset() {
        this.version = "2.0"; // the GLTF version
        this.generator = "Tyreogem";
    }

    public String getVersion() {
        return version;
    }

    public String getGenerator() {
        return generator;
    }
    
}
