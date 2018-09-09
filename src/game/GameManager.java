package game;

import static game.BracketBuster.SECOND_DELAY;

public class GameManager {
    private int level;
    private int score;
    private double timeLeft;
    private int numLives;
    private Player myPlayer;

    GameManager() {
        timeLeft = 60;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public double decrementTimer() {
        return timeLeft -= SECOND_DELAY;
    }
}
