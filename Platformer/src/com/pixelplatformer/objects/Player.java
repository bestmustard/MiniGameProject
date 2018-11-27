package com.pixelplatformer.objects;

import com.pixelplatformer.framework.GameObject;
import com.pixelplatformer.framework.ObjectId;
import com.pixelplatformer.framework.Texture;
import com.pixelplatformer.window.Animation;
import com.pixelplatformer.window.Camera;
import com.pixelplatformer.window.Game;
import com.pixelplatformer.window.Handler;

import java.awt.*;
import java.util.LinkedList;

public class Player extends GameObject {
    private float width = 32, height = 62;
    private float gravity = 0.5f;
    private final float MAX_SPEED = 10;
    private int facing = 1; // facing 1 = right, -1 = left

    private Handler handler;
    private Camera cam;

    Texture tex = Game.getInstance();

    private Animation playerWalk, playerWalkLeft;

    public Player(float x, float y, Handler handler, Camera cam, ObjectId id) {
        super(x, y, id);
        this.handler = handler;
        this.cam = cam;

        playerWalk = new Animation(2, tex.player[0], tex.player[1], tex.player[2], tex.player[3]);
        playerWalkLeft = new Animation(2, tex.player[4], tex.player[5], tex.player[6], tex.player[7]);

    }

    @Override
    public void tick(LinkedList<GameObject> object) {
        x += velX;
        y += velY;

        if (velX < 0) {
            facing = -1;
        } else {
            facing = 1;
        }

        if (falling || jumping) {
            velY+= gravity;

            if (velY > MAX_SPEED) {
                velY = MAX_SPEED;
            }
        }
        Collision(object);

        playerWalk.runAnimation();
        playerWalkLeft.runAnimation();

    }

    private void Collision(LinkedList<GameObject> object) {
        for (int i =  0; i<handler.object.size(); i++){
            GameObject tempObject = handler.object.get(i);
            if (tempObject.getId() == ObjectId.Block) {
                //top
                if (getBoundsTop().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() + 35;
                    velY = 0;
                }

                //right
                if (getBounds().intersects(tempObject.getBounds())) {
                    y = tempObject.getY() - height;
                    velY = 0;
                    falling = false;
                    jumping = false;
                } else {
                    falling = true;
                }
                //left
                if (getBoundsRight().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() - 32;
                }
                if (getBoundsLeft().intersects(tempObject.getBounds())) {
                    x = tempObject.getX() + 32;
                }
            } else if (tempObject.getId() == ObjectId.Flag) {
                if (getBounds().intersects(tempObject.getBounds())) {
                    handler.switchLevel();
                }
            } else if (tempObject.getY() > 500) {
                tempObject.setX(30);
                tempObject.setY(300);
            }
        }
    }
    @Override
    public void render(Graphics g) {
        g.setColor(Color.blue);
        if (jumping) {
            g.drawImage(tex.player[8], (int) x, (int) y, 48, 72, null);
        } else {
            if (velX!=0) {
                if (facing == 1) {
                    playerWalk.drawAnimation(g, (int) x, (int) y, 48, 72);
                } else {
                    playerWalkLeft.drawAnimation(g, (int) x, (int) y, 48, 72);
                }
            }
            else {
                if (facing == 1)
                    g.drawImage(tex.player[0], (int) x, (int) y, 48, 72, null);
                else if (facing == -1)
                    g.drawImage(tex.player[4], (int) x, (int) y, 48, 72, null);
            }
        }

/* drawing bounds for testing
        Graphics2D g2d = (Graphics2D) g;
        g.setColor(Color.red);
        g2d.draw(getBounds());
        g2d.draw(getBoundsRight());
        g2d.draw(getBoundsLeft());
        g2d.draw(getBoundsTop());
        */

    }
    @Override
    public Rectangle getBounds() {
        return new Rectangle((int)x+(int)width/2-((int)(width-width/2)/2), (int)y+(int)height/2, (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsTop() {
        return new Rectangle((int)x+(int)width/2-((int)(width-width/2)/2), (int)y, (int)width/2, (int)height/2);
    }
    public Rectangle getBoundsRight() {
        return new Rectangle((int)x+(int)width-5, (int)y+5, 5, (int)height-10);
    }
    public Rectangle getBoundsLeft() {
        return new Rectangle((int)x, (int)y+5, 5, (int)height-10);
    }

}
