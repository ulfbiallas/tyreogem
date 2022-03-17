package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.util.List;

public class GltfPbrMetallicRoughness {

    private final GltfMaterialTextureReference baseColorTexture;

    private final List<Double> baseColorFactor;

    private final double metallicFactor;

    private final double roughnessFactor;

    public GltfPbrMetallicRoughness(GltfMaterialTextureReference baseColorTexture, List<Double> baseColorFactor, double metallicFactor, double roughnessFactor) {
        this.baseColorTexture = baseColorTexture;
        this.baseColorFactor = baseColorFactor;
        this.metallicFactor = metallicFactor;
        this.roughnessFactor = roughnessFactor;
    }

    public GltfMaterialTextureReference getBaseColorTexture() {
        return baseColorTexture;
    }

    public List<Double> getBaseColorFactor() {
        return baseColorFactor;
    }

    public double getMetallicFactor() {
        return metallicFactor;
    }

    public double getRoughnessFactor() {
        return roughnessFactor;
    } 

}
