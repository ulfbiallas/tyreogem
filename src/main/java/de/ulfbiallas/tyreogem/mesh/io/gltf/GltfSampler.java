package de.ulfbiallas.tyreogem.mesh.io.gltf;

import com.fasterxml.jackson.annotation.JsonValue;

public class GltfSampler {

    enum SamplerMagFilter {

        NEAREST(9728),
        LINEAR(9729);

        public final int value;

        private SamplerMagFilter(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
    }

    enum SamplerMinFilter {

        NEAREST(9728),
        LINEAR(9729),
        NEAREST_MIPMAP_NEAREST(9984),
        LINEAR_MIPMAP_NEAREST(9985),
        NEAREST_MIPMAP_LINEAR(9986),
        LINEAR_MIPMAP_LINEAR(9987);

        public final int value;

        private SamplerMinFilter(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
    }

    enum SamplerWrapping {

        CLAMP_TO_EDGE(33071),
        MIRRORED_REPEAT(33648),
        REPEAT(10497);

        public final int value;

        private SamplerWrapping(int value) {
            this.value = value;
        }

        @JsonValue
        public int getValue() {
            return value;
        }
    }

    private final SamplerMagFilter magFilter; // magnification filter

    private final SamplerMinFilter minFilter; // minification filter

    private final SamplerWrapping wrapS;

    private final SamplerWrapping wrapT;

    public GltfSampler(SamplerMagFilter magFilter, SamplerMinFilter minFilter, SamplerWrapping wrapS, SamplerWrapping wrapT) {
        this.magFilter = magFilter;
        this.minFilter = minFilter;
        this.wrapS = wrapS;
        this.wrapT = wrapT;
    }

    public SamplerMagFilter getMagFilter() {
        return magFilter;
    }

    public SamplerMinFilter getMinFilter() {
        return minFilter;
    }

    public SamplerWrapping getWrapS() {
        return wrapS;
    }

    public SamplerWrapping getWrapT() {
        return wrapT;
    }

}
