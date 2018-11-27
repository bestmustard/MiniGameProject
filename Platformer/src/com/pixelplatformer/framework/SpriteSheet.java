package com.pixelplatformer.framework;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SpriteSheet {
    private BufferedImage image;

    public  SpriteSheet(BufferedImage image) {
        this.image = image;
    }
    public BufferedImage grabImage(int col, int row, int width, int height) {
        BufferedImage img = image.getSubimage(col*width - width, row*height-height, width, height);
        img = resize(img, 32, 32);
        return img;
    }

    public static BufferedImage resize(BufferedImage img, int newW, int newH) {
        Image tmp = img.getScaledInstance(newW, newH, Image.SCALE_SMOOTH);
        BufferedImage dimg = new BufferedImage(newW, newH, BufferedImage.TYPE_INT_ARGB);

        Graphics2D g2d = dimg.createGraphics();
        g2d.drawImage(tmp, 0, 0, null);
        g2d.dispose();

        return dimg;
    }
}
