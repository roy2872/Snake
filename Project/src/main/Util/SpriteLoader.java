package Project.src.main.Util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteLoader {

    public SpriteLoader(){

    }

    public BufferedImage loadSprite(String sprite){
        try {
            return ImageIO.read(new File("Project/src/main/Sprites/" + sprite + ".png"));
        } catch (IOException e) {
            throw new RuntimeException("Error loading sprite: " + sprite);
        }
    }

    public BufferedImage[] loadSprites(String[] spriteNames){
        BufferedImage[] sprites = new BufferedImage[spriteNames.length];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = loadSprite(spriteNames[i]);
        }
        return sprites;
    }
}
