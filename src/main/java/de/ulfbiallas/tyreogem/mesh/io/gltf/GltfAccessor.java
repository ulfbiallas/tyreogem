package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonInclude(Include.NON_NULL)
public class GltfAccessor {

    public enum AccessorComponentType {

        BYTE(5120),
        UNSIGNED_BYTE(5121),
        SHORT(5122),
        UNSIGNED_SHORT(5123),
        UNSIGNED_INT(5125),
        FLOAT(5126);

        public final int value;

        private AccessorComponentType(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
        
    }

    public enum AccessorType {
        SCALAR,
        VEC2,
        VEC3,
        VEC4,
        MAT2,
        MAT3,
        MAT4
    }

    private final int bufferView;

    private final int byteOffset;

    private final AccessorComponentType componentType;

    private final int count;

    private final AccessorType type;

    private final List<Double> min;

    private final List<Double> max;

    public GltfAccessor(int bufferView, int byteOffset, AccessorComponentType componentType, int count, AccessorType type, List<Double> min, List<Double> max) {
        this.bufferView = bufferView;
        this.byteOffset = byteOffset;
        this.componentType = componentType;
        this.count = count;
        this.type = type;
        this.min = min;
        this.max = max;
    }

    public int getBufferView() {
        return bufferView;
    }

    public int getByteOffset() {
        return byteOffset;
    }

    public AccessorComponentType getComponentType() {
        return componentType;
    }

    public int getCount() {
        return count;
    }

    public AccessorType getType() {
        return type;
    }

    public List<Double> getMin() {
        return min;
    }

    public List<Double> getMax() {
        return max;
    }
}
