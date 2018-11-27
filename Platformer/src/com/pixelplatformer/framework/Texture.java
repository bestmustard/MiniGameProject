package com.pixelplatformer.framework;

import com.pixelplatformer.window.BufferedImageLoader;

import java.awt.image.BufferedImage;

public class Texture {

    SpriteSheet bs, ps;
    private BufferedImage block_sheet = null;
    private BufferedImage player_sheet = null;

    public BufferedImage[] block = new BufferedImage[2];
    public BufferedImage[] player = new BufferedImage[9];


    public Texture() {

        BufferedImageLoader loader = new BufferedImageLoader();
        try{
            block_sheet = loader.loadImage("/block_sheet.png");
            player_sheet = loader.loadImage("/player_sheet.png");
        } catch(Exception e) {
            e.printStackTrace();
        }

        bs = new SpriteSheet(block_sheet);
        ps = new SpriteSheet(player_sheet);

        getTextures();

    }

    private void getTextures() {
        block[0] = bs.grabImage(7, 1, 16, 16);
        block[1] = bs.grabImage(5, 1, 16, 16);

        //right
        player[0] = ps.grabImage(1,3, 50, 50); //player
        player[1] = ps.grabImage(2,3, 50, 50); //player
        player[2] = ps.grabImage(3,3, 50, 50); //player
        player[3] = ps.grabImage(4,3, 50, 50); //player

        //left
        player[4] = ps.grabImage(1,2, 50, 50); //player
        player[5] = ps.grabImage(2,2, 50, 50); //player
        player[6] = ps.grabImage(3,2, 50, 50); //player
        player[7] = ps.grabImage(4,2, 50, 50); //player

        //jump
        player[8] = ps.grabImage(4,1, 50, 50); //player


    }

}
