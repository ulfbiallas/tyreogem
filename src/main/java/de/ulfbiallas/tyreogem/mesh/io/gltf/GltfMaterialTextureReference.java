package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfMaterialTextureReference {

    private final int index;

    private final int texCoord;

    public GltfMaterialTextureReference(int index, int texCoord) {
        this.index = index;
        this.texCoord = texCoord;
    }

    public int getIndex() {
        return index;
    }

    public int getTexCoord() {
        return texCoord;
    }

}
