package Project.src.main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.constant.DirectMethodHandleDesc;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Project.src.main.Constants.ConstSnake;
import Project.src.main.Util.Direction;
import Project.src.main.Util.Segment;
import Project.src.main.Util.SpriteLoader;
public class Snake {

    BufferedImage[] snakeSprites;
    SpriteLoader loader;
    LinkedList<Segment> body;

    int length;
    int speed;
    Direction direction;
    int headX;
    int headY;
    Graphics g;
    JPanel p;

    

    public Snake(Graphics graphics, JPanel panel){ 
        // Constructor
        length = 3;
        speed = 16;
        direction = Direction.RIGHT;
        headX = 80;
        headY = 160;

        loader = new SpriteLoader();
        body = new LinkedList<>();

        for (int i = 0; i < length; i++) {
    
                body.add(new Segment(headX - i * 16, headY, direction));
        }
            
            
        snakeSprites = loader.loadSprites(ConstSnake.SNAKE_SPRITE_NAMES);

        g = graphics;
        p = panel;

    }

    public void SnakeUpdate(){


        updateSpeeds();

        draw();

        body.addFirst(new Segment(headX, headY, direction));

        if (body.size() > length) {
            body.removeLast();
        }
        
    }


    

    public void switchDirection(Direction newDirection) {
        // Prevent reversing direction directly
        if ((direction == Direction.UP && newDirection == Direction.DOWN) ||
            (direction == Direction.DOWN && newDirection == Direction.UP) ||
            (direction == Direction.LEFT && newDirection == Direction.RIGHT) ||
            (direction == Direction.RIGHT && newDirection == Direction.LEFT)) {
            return;
        }
        this.direction = newDirection;
    }

    public void updateSpeeds(){
        switch (direction) {
            case UP:
                headY -= speed;
                break;
            case DOWN:
                headY += speed;
                break;
            case LEFT:
                headX -= speed;
                break;
            case RIGHT:
                headX += speed;
                break;
            default:
                break;
        }
    }

    public void grow(){
        length++;
    }

    public void draw() {
        for (int i = 0; i < body.size(); i++) {
            Segment segment = body.get(i);
            if (i == 0) {
                // Draw the head
                BufferedImage headSprite = getHeadSprite(body.get(i).direction);
                g.drawImage(headSprite, segment.x, segment.y, 16, 16, null);
            } else if (i == body.size() - 1) {
                // Draw the tail using the previous segment's direction
                BufferedImage tailSprite = getTailSprite(body.get(i - 1).direction);
                g.drawImage(tailSprite, segment.x, segment.y, 16, 16, null);
                
            } else {
                // Draw the body using the segment's direction
                BufferedImage bodySprite = getBodySprite(segment.direction);
                g.drawImage(bodySprite, segment.x, segment.y, 16, 16, null);
            }
        }
    }
        
    


    private BufferedImage getBodySprite(Direction direction) {
        // Return the appropriate sprite based on the segment's direction
        switch (direction) {
            case UP:
                return snakeSprites[9];
            case DOWN:
                return snakeSprites[9];
            case LEFT:
                return snakeSprites[8];
            case RIGHT:
                return snakeSprites[8];
            default:
                return null;
        }
    }

    private BufferedImage getHeadSprite(Direction direction) {
        // Return the appropriate sprite based on the segment's direction
        switch (direction) {
            case UP:
                return snakeSprites[0];
            case DOWN:
                return snakeSprites[1];
            case LEFT:
                return snakeSprites[2];
            case RIGHT:
                return snakeSprites[3];
            default:
                return null;
        }
    }

    private BufferedImage getTailSprite(Direction direction){
        switch (direction) {
            case UP:
                return snakeSprites[4];
            case DOWN:
                return snakeSprites[5];
            case LEFT:
                return snakeSprites[6];
            case RIGHT:
                return snakeSprites[7];
            default:
                return null;
        }
    }


    public int getHeadX(){
        return headX;
    }

    public int getHeadY(){
        return headY;
    }

    public int getLength(){
        return length;
    }

    public Direction getDirection(){
        return direction;
    }

    public LinkedList<Segment> getBody(){
        return body;
    }


    public boolean isCollidingWithSelf(){
        for (int i = 1; i < body.size(); i++) {
            if (headX == body.get(i).x && headY == body.get(i).y) {
                return true;
            }
        }
        return false;
    }




    
}
