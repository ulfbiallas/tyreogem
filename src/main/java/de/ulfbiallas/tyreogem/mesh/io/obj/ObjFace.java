package de.ulfbiallas.tyreogem.mesh.io.obj;

import java.util.List;

public class ObjFace {

    private List<ObjFaceIndex> indices;
    private String materialName;

    public ObjFace(List<ObjFaceIndex> indices, String materialName) {
        this.indices = indices;
        this.materialName = materialName;
    }

    public ObjFace(List<ObjFaceIndex> indices) {
        this(indices, ObjMaterial.NO_MATERIAL);
    }

    public List<ObjFaceIndex> getIndices() {
        return indices;
    }

    public String getMaterialName() {
        return materialName;
    }

}
