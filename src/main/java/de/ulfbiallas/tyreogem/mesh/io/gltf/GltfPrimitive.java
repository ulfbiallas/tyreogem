package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class GltfPrimitive {

    public enum MeshPrimitiveMode {

        POINTS(0),
        LINES(1),
        LINE_LOOP(2),
        LINE_STRIP(3),
        TRIANGLES(4),
        TRIANGLE_STRIP(5),
        TRIANGLE_FAN(6);

        public final int value;

        private MeshPrimitiveMode(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
    }

    final private Map<String, Integer> attributes;

    final private Integer indices;

    final private MeshPrimitiveMode mode;

    final private Integer material;

    public GltfPrimitive(Map<String, Integer> attributes, Integer indices, MeshPrimitiveMode mode, Integer material) {
        this.attributes = attributes;
        this.indices = indices;
        this.mode = mode;
        this.material = material;
    }

    public Map<String, Integer> getAttributes() {
        return attributes;
    }

    public Integer getIndices() {
        return indices;
    }

    public MeshPrimitiveMode getMode() {
        return mode;
    }

    public Integer getMaterial() {
        return material;
    }

}
