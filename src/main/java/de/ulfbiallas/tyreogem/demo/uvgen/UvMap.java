package de.ulfbiallas.tyreogem.demo.uvgen;

import de.ulfbiallas.tyreogem.core.math.Vec2d;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UvMap {

    private static final int LUMELS_PER_WORLD_UNIT = 4; // lumel = lumination element

    private final List<UvGenFace> uvGenFaces = new ArrayList<>();

    private final int MARGIN = 2;

    private final int width;

    private final int height;

    public UvMap(List<UvGenFace> uvGenFaces) {
        Vec2d offset = new Vec2d(MARGIN, MARGIN);

        final int maxDimX = (int) Math.ceil(uvGenFaces.stream().map(f -> f.getSize().x).max(Double::compare).get() * LUMELS_PER_WORLD_UNIT);
        final int maxDimY = (int) Math.ceil(uvGenFaces.stream().map(f -> f.getSize().y).max(Double::compare).get() * LUMELS_PER_WORLD_UNIT);
        final int maxDim = Math.max(maxDimX, maxDimY);
        final int itemsPerRow = (int) Math.ceil(Math.sqrt(uvGenFaces.size()));

        for(int k=0; k<uvGenFaces.size(); ++k) {
            UvGenFace uvGenFace = uvGenFaces.get(k);

            uvGenFace.setUvOffset(offset);
            this.uvGenFaces.add(uvGenFace);

            if((k+1) % itemsPerRow == 0) {
                offset = new Vec2d(MARGIN, offset.y + maxDim + MARGIN);
            } else {
                offset = offset.add(new Vec2d(maxDim + MARGIN, 0));
            }
        }

        this.width = itemsPerRow * maxDim + (itemsPerRow+1) * MARGIN;
        this.height = this.width;
    }

    public void updateTextureCoordinates() {
        for(UvGenFace uvGenFace : this.uvGenFaces) {
            uvGenFace.updateTextureCoordinates(this.width, this.height, LUMELS_PER_WORLD_UNIT);
        }
    }

    public void writeFile(File file) {
        BufferedImage bufferedImage = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB);

        List<Color> colors = Arrays.asList(Color.BLUE, Color.RED, Color.GREEN);

        Graphics2D graphics2d = bufferedImage.createGraphics();

        for(int k=0; k<this.uvGenFaces.size(); ++k) {
            graphics2d.setColor(colors.get(k % colors.size()));
            this.uvGenFaces.get(k).draw(graphics2d, LUMELS_PER_WORLD_UNIT);
        }

        graphics2d.dispose();

        try {
            ImageIO.write(bufferedImage, "png", file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
