package Project;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Project.Util.*;

public class Fruit {

    int fruitX;
    int fruitY;
    FruitType type;
    SpriteLoader spriteLoader;
    BufferedImage sprite;
    JPanel panel;
    Graphics graphics;
    
    public Fruit(FruitType fruitType, JPanel p, Graphics g, int x, int y){
        
        spriteLoader = new SpriteLoader();
        this.type = fruitType;

        sprite = spriteLoader.loadSprite(fruitType.toString());

        // Set the fruit position
        panel = p;
        graphics = g;
        fruitX = x;
        fruitY = y;

        draw();

    }

    
    public void draw(){
        // Draw the fruits
        graphics.drawImage(sprite, fruitX, fruitY, null);
    }




}
