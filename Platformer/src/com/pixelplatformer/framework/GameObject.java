package com.pixelplatformer.framework;

import java.awt.*;
import java.util.LinkedList;

public abstract class GameObject {

    protected float x, y;
    protected ObjectId id;
    protected float velX = 0, velY = 0;
    protected boolean falling = true;
    protected boolean jumping = false;

    public GameObject(float x, float y, ObjectId id) {
        this.x = x;
        this.y = y;
        this.id = id;
    }

    public abstract void tick (LinkedList<GameObject> object);
    public abstract void render (Graphics g);
    public abstract Rectangle getBounds();

    public float getX() {
        return x;
    }
    public float getY(){
        return y;
    }
    public void setX(float x) {
        this.x = x;
    }
    public void setY(float y){
        this.y = y;
    }

    public float getVelX(){
        return velX;
    }
    public float getVelY(){
        return velY;
    }
    public void setVelX(float velX){
        this.velX = velX;
    }
    public void setVelY(float velY){
        this.velY = velY;
    }

    public boolean isFalling() {
        return falling;
    }

    public void setFalling(boolean falling) {
        this.falling = falling;
    }

    public boolean isJumping() {
        return jumping;
    }

    public void setJumping(boolean jumping) {
        this.jumping = jumping;
    }

    public ObjectId getId() {
        return id;
    }
}
