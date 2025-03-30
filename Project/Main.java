package Project;

import java.time.Period;
import java.util.Timer;
import java.util.TimerTask;

public class Main {

    static Timer periodic;
        
    public static void main(String[] args) {
        // Create a new instance of the game
        Game game = new Game();
        // Start the game
        game.start();
    
        periodic = new Timer();
        periodic.schedule(
            new TimerTask() {
                @Override
                public void run() {
                    game.update();
                }
            },
        5000, 200
        );




        
    }
    
}