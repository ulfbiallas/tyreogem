package de.ulfbiallas.tyreogem.mesh.io.obj;

public class ObjFaceIndex {

    private final Integer vertexIndex;
    private final Integer textureCoordinatesIndex;
    private final Integer vertexNormalIndex;

    public ObjFaceIndex(Integer vertexIndex, Integer textureCoordinatesIndex, Integer vertexNormalIndex) {
        this.vertexIndex = vertexIndex;
        this.textureCoordinatesIndex = textureCoordinatesIndex;
        this.vertexNormalIndex = vertexNormalIndex;
    }

    public Integer getVertexIndex() {
        return vertexIndex;
    }

    public Integer getTextureCoordinatesIndex() {
        return textureCoordinatesIndex;
    }

    public Integer getVertexNormalIndex() {
        return vertexNormalIndex;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof ObjFaceIndex)) {
            ObjFaceIndex idx = (ObjFaceIndex) obj;
            return idx.vertexIndex.equals(this.vertexIndex) && idx.textureCoordinatesIndex.equals(this.textureCoordinatesIndex) && idx.vertexNormalIndex.equals(this.vertexNormalIndex);
        }
        return false;
    }

    @Override
    public String toString() {
        return "ObjFaceIndex [vertexIndex=" + vertexIndex + ", textureCoordinatesIndex=" + textureCoordinatesIndex
                + ", vertexNormalIndex=" + vertexNormalIndex + "]";
    }

}
