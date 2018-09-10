package game;

import static game.BracketBuster.SECOND_DELAY;

public class GameManager {
    public static final int TOTAL_TIME = 60;
    public static final int TOTAL_LIVES = 3;

    private double timeLeft;
    private int score;
    private int numLives;
    private boolean frozen;

    GameManager() {
        timeLeft = TOTAL_TIME;
        numLives = TOTAL_LIVES;
        frozen = false;
    }

    public double getTimeLeft() {
        return timeLeft;
    }

    public double decrementTimer() {
        return timeLeft -= SECOND_DELAY;
    }

    public void incrementTimer(int addTime) {
        timeLeft += addTime;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int points) {
        score += points;
    }

    public int getLivesLeft() {
        return numLives;
    }

    public void setLives(int addLives) {
        numLives += addLives;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void freezeTime() {
        frozen = true;
    }

    public void unfreezeTime() {
        frozen = false;
    }
}
