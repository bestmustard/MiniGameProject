package com.pixelplatformer.objects;

import com.pixelplatformer.framework.GameObject;
import com.pixelplatformer.framework.ObjectId;

import java.awt.*;
import java.util.LinkedList;

public class Flag extends GameObject {

    public Flag(float x, float y, ObjectId id) {
        super(x, y, id);
    }

    @Override
    public void tick(LinkedList<GameObject> object) {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.yellow);
        g.fillRect((int)x, (int)y, 32, 32);
    }

    public Rectangle getBounds() {
        return new Rectangle ((int) x, (int) y, 32, 32);
    }
}
