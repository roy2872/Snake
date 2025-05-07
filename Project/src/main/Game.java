package Project.src.main;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JWindow;

import Project.src.main.Util.CustomKeyListener;
import Project.src.main.Util.Direction;
import Project.src.main.Util.FruitType;

public class Game {
    
    // JWindow window;
    JFrame frame;
    JPanel panel;
    Graphics g;
    Snake snake;
    Fruit fruit;
    Random random;
    int curFruitPosX;
    int curFruitPosY;
    boolean directionChanged;
    Project.src.main.Util.Direction bufferedDirection;
    Dimension screenSize;
    

    public Game() {
        directionChanged = false;
        // Constructor
        // window = new JWindow();
        panel = new JPanel();
        frame = new JFrame();
        screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        
        

        frame.setVisible(true);
        bufferedDirection = null;
        frame.add(panel);
        panel.setDoubleBuffered(true);
        // window.add(frame);
        frame.setSize(320, 320);
        frame.setLocation(screenSize.width/2-180, screenSize.height/2-180);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setFocusable(true);
        panel.setName("Snake Game");

        g = panel.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());
        // int panelWidth = panel.getWidth();
        // int panelHeight = panel.getHeight();
        // int gridSize = snake.speed; // Assuming the snake's speed is the grid size

        // curFruitPosX = (int) (Math.random() * (panelWidth / gridSize)) * gridSize;
        // curFruitPosY = (int) (Math.random() * (panelHeight / gridSize)) * gridSize;
    
        random = new Random();
        panel.addKeyListener(new CustomKeyListener() {


            public void keyPressed(java.awt.event.KeyEvent e) {
                switch (e.getKeyCode()) {
                    case java.awt.event.KeyEvent.VK_UP,java.awt.event.KeyEvent.VK_W:
                        switchDirection(Direction.UP);

                        break;
                    case java.awt.event.KeyEvent.VK_DOWN,java.awt.event.KeyEvent.VK_S:
                        switchDirection(Direction.DOWN);

                        break;
                    case java.awt.event.KeyEvent.VK_LEFT,java.awt.event.KeyEvent.VK_A:
                        switchDirection(Direction.LEFT);

                        break;
                    case java.awt.event.KeyEvent.VK_RIGHT,java.awt.event.KeyEvent.VK_D:
                        switchDirection(Direction.RIGHT);

                        break;
                    default:

                        break;
                }
            }
        });
        
    }

    public void start() {
        // Start the game
        snake = new Snake(panel.getGraphics(), panel);
        createFruit();
    }

    public void update() {
        directionChanged = false; // Allow direction changes for this update cycle
    
        // Apply the buffered direction if it's valid
        if (bufferedDirection != null) {
            if (!isReversingDirection(bufferedDirection)) {
                snake.switchDirection(bufferedDirection);
            }
            bufferedDirection = null; // Clear the buffer after checking
        }
    
        panel.getGraphics().setColor(Color.BLACK);
        panel.getGraphics().fillRect(0, 0, panel.getWidth(), panel.getHeight());
        collisionWithFruit();
        fruit.draw();
        if (endGameCondition()) {
            endGame();
        }
        snake.SnakeUpdate();

    }

    public void switchDirection(Direction direction) {
        if (!isReversingDirection(direction)) {
            bufferedDirection = direction; // Buffer the direction if it's valid
        }
        bufferedDirection = direction;
    }

    public void collisionWithFruit(){

        if (snake.body.getFirst().x == fruit.fruitX && snake.body.getFirst().y == fruit.fruitY) {
            snake.grow();
            createFruit();
            
        }
    }

    public FruitType randomFruitSelector(){
        // Randomly select a fruit
        return FruitType.values()[(int)(Math.random() * FruitType.values().length)];
    }

    public void createFruit() {
        int panelWidth = panel.getWidth();
        int panelHeight = panel.getHeight();
        int gridSize = snake.speed; // Assuming the snake's speed is the grid size
    
        int fruitX, fruitY;
        boolean isValidPosition;
    
        do {
            // Generate random x and y positions aligned to the grid
            fruitX = (int) (Math.random() * (panelWidth / gridSize)) * gridSize;
            fruitY = (int) (Math.random() * (panelHeight / gridSize)) * gridSize;
    
            // Check if the position overlaps with the snake
            isValidPosition = true;
            for (Project.src.main.Util.Segment segment : snake.body) {
                if (segment.x == fruitX && segment.y == fruitY) {
                    isValidPosition = false;
                    break;
                }
            }
        } while (!isValidPosition); // Repeat until a valid position is found
    
        // Create the fruit at the valid position
        fruit = new Fruit(this.randomFruitSelector(), panel, panel.getGraphics(), fruitX, fruitY);
    }

    public boolean endGameCondition(){

        return (snake.headX < 0 || snake.headX >= panel.getWidth() || snake.headY < 0 || snake.headY >= panel.getHeight()) || snake.isCollidingWithSelf();
    }

    public void endGame(){
        // End the game
        JOptionPane.showMessageDialog(null, "Game Over!");
        System.exit(0);
    }

    private boolean isReversingDirection(Direction newDirection) {
        // Check if the new direction is the opposite of the current direction
        return (snake.direction == Direction.UP && newDirection == Direction.DOWN) ||
               (snake.direction == Direction.DOWN && newDirection == Direction.UP) ||
               (snake.direction == Direction.LEFT && newDirection == Direction.RIGHT) ||
               (snake.direction == Direction.RIGHT && newDirection == Direction.LEFT);
    }
}
