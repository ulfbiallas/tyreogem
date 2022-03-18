package de.ulfbiallas.tyreogem.mesh.io.gltf;

public class GltfBufferView {

    final private int buffer;

    final private int byteOffset;

    final private int byteLength;

    public GltfBufferView(int buffer, int byteOffset, int byteLength) {
        this.buffer = buffer;
        this.byteOffset = byteOffset;
        this.byteLength = byteLength;
    }

    public int getBuffer() {
        return buffer;
    }

    public int getByteOffset() {
        return byteOffset;
    }

    public int getByteLength() {
        return byteLength;
    }

}
