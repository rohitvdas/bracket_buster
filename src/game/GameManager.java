package game;

import java.util.Timer;
import java.util.TimerTask;

public class GameManager {
    private int level;
    private int score;
    private Timer clock;
    private int numLives;

    GameManager() {
        clock = new Timer();
        clock.scheduleAtFixedRate(new TimerTask() {
            int i = 60;
            public void run() {
                System.out.println(i--);
                if(i < 0) {
                    clock.cancel();
                }
            }
        }, 0, 1000);
    }
}
