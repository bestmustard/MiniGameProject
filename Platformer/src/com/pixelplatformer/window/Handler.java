package com.pixelplatformer.window;

import com.pixelplatformer.framework.GameObject;
import com.pixelplatformer.framework.ObjectId;
import com.pixelplatformer.objects.Block;
import com.pixelplatformer.objects.Flag;
import com.pixelplatformer.objects.Player;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

public class Handler {
    public LinkedList <GameObject> object = new LinkedList<GameObject>();

    private GameObject tempObject;

    private Camera cam;

    private BufferedImage level2 = null, level3 = null, level4 = null, level5 = null ,level6 = null;

    public Handler (Camera cam) {
        BufferedImageLoader loader = new BufferedImageLoader();
        level2 = loader.loadImage("/level2.png"); //load level
        level3 = loader.loadImage("/level3.png"); //load level
        level4 = loader.loadImage("/level4.png"); //load level
        level5 = loader.loadImage("/level5.png"); //load level
        level6 = loader.loadImage("/final.png"); //load level

        this.cam = cam;
    }

    public void tick() {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.tick(object);
        }
    }

    public void render (Graphics g) {
        for (int i = 0; i < object.size(); i++) {
            tempObject = object.get(i);
            tempObject.render(g);
        }
    }

    public void loadImageLevel(BufferedImage image) {
        int w = image.getWidth();
        int h = image.getHeight();

        for (int xx = 0; xx< h; xx++) {
            for(int yy = 0; yy< w; yy++) {
                int pixel  = image.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue  = (pixel) & 0xff;

                if (red == 255 && green == 255 & blue == 255) {
                    addObject(new Block(xx*32, yy*32, 0, ObjectId.Block));
                }
                if (red == 0 && green == 0 & blue == 255) {
                    addObject(new Player(xx*32, yy*32, this, cam, ObjectId.Player));
                }
                if (red == 255 && green == 255 & blue == 0) {
                    addObject(new Flag(xx*32, yy*32, ObjectId.Flag));
                }

            }
        }

    }
    public void switchLevel() {
        clearLevel();
        cam.setX(0);

        switch(Game.LEVEL) {
            case 1:
                loadImageLevel(level2);
                break;
            case 2:
                loadImageLevel(level3);
                break;
            case 3:
                loadImageLevel(level4);
                break;
            case 4:
                loadImageLevel(level5);
                break;
            case 5:
                loadImageLevel(level6);
                break;
        }
        Game.LEVEL++;

    }

    private void clearLevel()  {
        object.clear();
        cam.setX(0);
    }

    public void addObject(GameObject object) {
        this.object.add(object);
    }

    public void removeObject(GameObject object) {
        this.object.remove(object);
    }

}
