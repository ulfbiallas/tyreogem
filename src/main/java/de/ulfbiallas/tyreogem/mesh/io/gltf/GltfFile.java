package de.ulfbiallas.tyreogem.mesh.io.gltf;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import de.ulfbiallas.tyreogem.core.math.Vec2d;
import de.ulfbiallas.tyreogem.core.math.Vec3d;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfAccessor.AccessorComponentType;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfAccessor.AccessorType;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfSampler.SamplerMagFilter;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfSampler.SamplerMinFilter;
import de.ulfbiallas.tyreogem.mesh.io.gltf.GltfSampler.SamplerWrapping;

@JsonInclude(Include.NON_NULL)
public class GltfFile {

    private final GltfAsset asset;

    private Integer scene;

    private final List<GltfScene> scenes;

    private final List<GltfNode> nodes;

    private final List<GltfMesh> meshes;

    private final List<GltfBuffer> buffers;

    private final List<GltfBufferView> bufferViews;

    private final List<GltfAccessor> accessors;

    private final List<GltfMaterial> materials;

    private final List<GltfTexture> textures;

    private final List<GltfImage> images;

    private final List<GltfSampler> samplers;

    public GltfFile(GltfAsset asset) {
        this.asset = asset;
        this.scene = null;
        this.scenes = new ArrayList<>();
        this.nodes = new ArrayList<>();
        this.meshes = new ArrayList<>();
        this.buffers = new ArrayList<>();
        this.bufferViews = new ArrayList<>();
        this.accessors = new ArrayList<>();
        this.materials = new ArrayList<>();
        this.textures = new ArrayList<>();
        this.images = new ArrayList<>();
        this.samplers = new ArrayList<>();
    }

    public int setPoints(List<Vec3d> points) {
        final int vec3fByteSize = 12; // 3 * 4 byte
        final int bufferLength = points.size() * vec3fByteSize;
        final ByteBuffer data = ByteBuffer.allocate(bufferLength);
        data.order(ByteOrder.LITTLE_ENDIAN);
        for(int k=0; k<points.size(); ++k) {
            final Vec3d p = points.get(k);
            data.position(k * vec3fByteSize + 0);
            data.putFloat((float) p.x);
            data.position(k * vec3fByteSize + 4);
            data.putFloat((float) p.y);
            data.position(k * vec3fByteSize + 8);
            data.putFloat((float) p.z);
        }

        final byte[] encoded = Base64.getEncoder().encode(data.array());
        final String bufferBase64 = new String(encoded);
        final String uri = "data:application/gltf-buffer;base64," + bufferBase64;
        final GltfBuffer gltfBuffer = new GltfBuffer(uri, bufferLength);
        final int bufferIndex = this.buffers.size();
        this.buffers.add(gltfBuffer);

        final int bufferViewIndex = this.bufferViews.size();
        this.bufferViews.add(new GltfBufferView(bufferIndex, 0, gltfBuffer.getByteLength()));

        final double minX = points.stream().map(p -> p.x).min(Double::compareTo).get();
        final double minY = points.stream().map(p -> p.y).min(Double::compareTo).get();
        final double minZ = points.stream().map(p -> p.z).min(Double::compareTo).get();
        final double maxX = points.stream().map(p -> p.x).max(Double::compareTo).get();
        final double maxY = points.stream().map(p -> p.y).max(Double::compareTo).get();
        final double maxZ = points.stream().map(p -> p.z).max(Double::compareTo).get();

        final int accessorIndex = this.accessors.size();
        this.accessors.add(new GltfAccessor(bufferViewIndex, 0, AccessorComponentType.FLOAT, points.size(), AccessorType.VEC3, Arrays.asList(minX, minY, minZ), Arrays.asList(maxX, maxY, maxZ)));

        return accessorIndex;
    }

    public int setNormals(List<Vec3d> normals) {
        if(normals.isEmpty()) {
            return -1;
        }

        final int vec3fByteSize = 12; // 3 * 4 byte
        final int bufferLength = normals.size() * vec3fByteSize;
        final ByteBuffer data = ByteBuffer.allocate(bufferLength);
        data.order(ByteOrder.LITTLE_ENDIAN);
        for(int k=0; k<normals.size(); ++k) {
            final Vec3d p = normals.get(k);
            data.position(k * vec3fByteSize + 0);
            data.putFloat((float) p.x);
            data.position(k * vec3fByteSize + 4);
            data.putFloat((float) p.y);
            data.position(k * vec3fByteSize + 8);
            data.putFloat((float) p.z);
        }

        final byte[] encoded = Base64.getEncoder().encode(data.array());
        final String bufferBase64 = new String(encoded);
        final String uri = "data:application/gltf-buffer;base64," + bufferBase64;
        final GltfBuffer gltfBuffer = new GltfBuffer(uri, bufferLength);
        final int bufferIndex = this.buffers.size();
        this.buffers.add(gltfBuffer);

        final int bufferViewIndex = this.bufferViews.size();
        this.bufferViews.add(new GltfBufferView(bufferIndex, 0, gltfBuffer.getByteLength()));

        final double minX = normals.stream().map(p -> p.x).min(Double::compareTo).get();
        final double minY = normals.stream().map(p -> p.y).min(Double::compareTo).get();
        final double minZ = normals.stream().map(p -> p.z).min(Double::compareTo).get();
        final double maxX = normals.stream().map(p -> p.x).max(Double::compareTo).get();
        final double maxY = normals.stream().map(p -> p.y).max(Double::compareTo).get();
        final double maxZ = normals.stream().map(p -> p.z).max(Double::compareTo).get();

        final int accessorIndex = this.accessors.size();
        this.accessors.add(new GltfAccessor(bufferViewIndex, 0, AccessorComponentType.FLOAT, normals.size(), AccessorType.VEC3, Arrays.asList(minX, minY, minZ), Arrays.asList(maxX, maxY, maxZ)));

        return accessorIndex;
    }

    public int setTextureCoordinates(List<Vec2d> texCoords) {
        if(texCoords.isEmpty()) {
            return -1;
        }

        final int vec2fByteSize = 8; // 2 * 4 byte
        final int bufferLength = texCoords.size() * vec2fByteSize;
        final ByteBuffer data = ByteBuffer.allocate(bufferLength);
        data.order(ByteOrder.LITTLE_ENDIAN);
        for(int k=0; k<texCoords.size(); ++k) {
            final Vec2d uv = texCoords.get(k);
            data.position(k * vec2fByteSize + 0);
            data.putFloat((float) uv.x);
            data.position(k * vec2fByteSize + 4);
            data.putFloat((float) uv.y);
        }

        final byte[] encoded = Base64.getEncoder().encode(data.array());
        final String bufferBase64 = new String(encoded);
        final String uri = "data:application/gltf-buffer;base64," + bufferBase64;
        final GltfBuffer gltfBuffer = new GltfBuffer(uri, bufferLength);
        final int bufferIndex = this.buffers.size();
        this.buffers.add(gltfBuffer);

        final int bufferViewIndex = this.bufferViews.size();
        this.bufferViews.add(new GltfBufferView(bufferIndex, 0, gltfBuffer.getByteLength()));

        final double minX = texCoords.stream().map(p -> p.x).min(Double::compareTo).get();
        final double minY = texCoords.stream().map(p -> p.y).min(Double::compareTo).get();
        final double maxX = texCoords.stream().map(p -> p.x).max(Double::compareTo).get();
        final double maxY = texCoords.stream().map(p -> p.y).max(Double::compareTo).get();

        final int accessorIndex = this.accessors.size();
        this.accessors.add(new GltfAccessor(bufferViewIndex, 0, AccessorComponentType.FLOAT, texCoords.size(), AccessorType.VEC2, Arrays.asList(minX, minY), Arrays.asList(maxX, maxY)));

        return accessorIndex;
    }

    public int setIndices(List<Integer> indices) {
        final int intByteSize = 4; // 1 * 4 byte
        final int bufferLength = indices.size() * intByteSize;
        System.out.println("bufferLength: " + bufferLength);
        final ByteBuffer data = ByteBuffer.allocate(bufferLength);
        data.order(ByteOrder.LITTLE_ENDIAN);
        for(int k=0; k<indices.size(); ++k) {
            final long idx = indices.get(k);
            data.position(k * intByteSize + 0);
            data.putInt((int) (idx & 0xffffffffL)); // puts unsigned int
        }

        final byte[] encoded = Base64.getEncoder().encode(data.array());
        final String bufferBase64 = new String(encoded);
        final String uri = "data:application/gltf-buffer;base64," + bufferBase64;
        final GltfBuffer gltfBuffer = new GltfBuffer(uri, bufferLength);
        final int bufferIndex = this.buffers.size();
        this.buffers.add(gltfBuffer);

        final int bufferViewIndex = this.bufferViews.size();
        this.bufferViews.add(new GltfBufferView(bufferIndex, 0, gltfBuffer.getByteLength()));

        final double min = indices.stream().min(Integer::compareTo).get();
        final double max = indices.stream().max(Integer::compareTo).get();

        final int accessorIndex = this.accessors.size();
        this.accessors.add(new GltfAccessor(bufferViewIndex, 0, AccessorComponentType.UNSIGNED_INT, indices.size(), AccessorType.SCALAR, Arrays.asList(min), Arrays.asList(max)));

        return accessorIndex;
    }

    public void addMesh(GltfMesh mesh) {
        final int meshIndex = this.meshes.size();
        this.meshes.add(mesh);

        final int nodeIndex = this.nodes.size();
        this.nodes.add(new GltfNode(meshIndex));

        final int sceneIndex = this.scenes.size();
        this.scenes.add(new GltfScene(Arrays.asList(nodeIndex)));

        if(scene == null) {
            this.scene = sceneIndex;
        }
    }

    public int addTexture(File textureFile) {
        try {
            InputStream inputStream = new FileInputStream(textureFile);
            ByteBuffer data = ByteBuffer.allocate(inputStream.available());
            data.order(ByteOrder.LITTLE_ENDIAN);
            Channels.newChannel(inputStream).read(data);

            final byte[] encoded = Base64.getEncoder().encode(data.array());
            final String bufferBase64 = new String(encoded);
            final String uri = "data:application/gltf-buffer;base64," + bufferBase64;
            final GltfBuffer gltfBuffer = new GltfBuffer(uri, data.capacity());
            final int bufferIndex = this.buffers.size();
            this.buffers.add(gltfBuffer);

            final int bufferViewIndex = this.bufferViews.size();
            this.bufferViews.add(new GltfBufferView(bufferIndex, 0, gltfBuffer.getByteLength()));

            final String mimeType = getMimeType(textureFile);

            final int imageIndex = this.images.size();
            this.images.add(new GltfImage(bufferViewIndex, mimeType));

            final int samplerIndex = this.samplers.size();
            this.samplers.add(new GltfSampler(SamplerMagFilter.LINEAR, SamplerMinFilter.LINEAR, SamplerWrapping.REPEAT, SamplerWrapping.REPEAT));

            final int textureIndex = this.textures.size();
            this.textures.add(new GltfTexture(samplerIndex, imageIndex));

            final int materialIndex = this.materials.size();
            final GltfMaterialTextureReference gltfMaterialTextureReference = new GltfMaterialTextureReference(textureIndex, 0);
            final GltfPbrMetallicRoughness gltfPbrMetallicRoughness = new GltfPbrMetallicRoughness(gltfMaterialTextureReference, Arrays.asList(1.0, 1.0, 1.0, 1.0), 0.5, 0.5);
            this.materials.add(new GltfMaterial(gltfPbrMetallicRoughness));
            
            return materialIndex;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private String getMimeType(File file) {
        final String[] components = file.getAbsolutePath().split("\\.");
        final String suffix = components.length > 0 ? components[components.length-1] : null;
        if(suffix != null) {
            if(suffix.toLowerCase().equals("jpg")) {
                return "image/jpeg";
            } else if(suffix.toLowerCase().equals("png")) {
                return "image/png";
            }
        }
        return "application/octet-stream";
    }

    public GltfAsset getAsset() {
        return asset;
    }

    public Integer getScene() {
        return scene;
    }

    public List<GltfScene> getScenes() {
        return scenes;
    }

    public List<GltfNode> getNodes() {
        return nodes;
    }

    public List<GltfMesh> getMeshes() {
        return meshes;
    }

    public List<GltfBuffer> getBuffers() {
        return buffers;
    }

    public List<GltfBufferView> getBufferViews() {
        return bufferViews;
    }

    public List<GltfAccessor> getAccessors() {
        return accessors;
    }

    public List<GltfMaterial> getMaterials() {
        return materials;
    }

    public List<GltfTexture> getTextures() {
        return textures;
    }

    public List<GltfImage> getImages() {
        return images;
    }

    public List<GltfSampler> getSamplers() {
        return samplers;
    }

}
