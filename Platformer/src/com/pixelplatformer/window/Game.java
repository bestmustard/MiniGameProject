package com.pixelplatformer.window;

import com.pixelplatformer.framework.KeyInput;
import com.pixelplatformer.framework.ObjectId;
import com.pixelplatformer.framework.Texture;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    private boolean running = false;
    private Thread thread;

    public static int WIDTH, HEIGHT;

    private BufferedImage bg = null;

    public BufferedImage level = null, level2 = null;

    //object
    Handler handler;
    Camera cam;
    static Texture tex;

    public static int LEVEL = 1;

    private void init() {

        WIDTH = getWidth();
        HEIGHT = getHeight();

        tex = new Texture();

        BufferedImageLoader loader = new BufferedImageLoader();
        level = loader.loadImage("/level.png"); //load level
        level2 = loader.loadImage("/level2.png"); //load level

        bg =  loader.loadImage("/bkg_flowers.png");

        cam = new Camera(0, 0);

        handler = new Handler(cam);

        handler.loadImageLevel(level);


        this.addKeyListener(new KeyInput(handler));
    }
    public synchronized void start() {
        if (running)
            return;
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void run () {
        init();
        this.requestFocus();
        System.out.println("startup");
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;
        while(running){
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while(delta >= 1){
                tick();
                updates++;
                delta--;
            }
            render();
            frames++;

            if(System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                System.out.println("FPS: " + frames + " TICKS: " + updates);
                frames = 0;
                updates = 0;
            }
        }
    }
    private void tick() {
        handler.tick();
        for (int i = 0; i<handler.object.size(); i++) {
            if (handler.object.get(i).getId() == ObjectId.Player) {
                cam.tick(handler.object.get(i));
            }
        }
    }
    private void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if (bs == null) {
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2d =  (Graphics2D) g;


        Color niceBlue = new Color(78,173,245);

        g.setColor(niceBlue);
        g.fillRect(0,0, getWidth(),getHeight());


        g2d.translate(cam.getX(), cam.getY()); //beginning of camera

        for (int xx = 0; xx< bg.getWidth() * 5; xx+=bg.getWidth()) {
                g.drawImage(bg, xx, 0, this);
            }
        handler.render(g);


        g2d.translate(-cam.getX(), -cam.getY()); //ending of camera

        g.dispose();
        bs.show();
    }



    public static Texture getInstance() {
        return tex;
    }

    public static void main (String args[]) {
        new Window(800, 600, "Pixel Platform Game", new Game());
    }
}
