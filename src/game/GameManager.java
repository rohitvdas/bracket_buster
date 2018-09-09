package game;

import static game.BracketBuster.SECOND_DELAY;

public class GameManager {
    private int level;
    private Player myPlayer;
    private double timeLeft;
    private int score;
    private int numLives;
    private boolean frozen = false;

    GameManager() {
        timeLeft = 60;
        numLives = 3;
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
