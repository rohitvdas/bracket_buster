package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp extends ImageView {
    public static final int POWER_UP_SPEED = 100;
    public static final int POWER_UP_SIZE = 50;

    private int addedPoints;
    private int addedLives;
    private int addedTime;

    PowerUp(Image image, int pointsToAdd, int livesToAdd, int timeToAdd) {
        super(image);
        this.setFitWidth(POWER_UP_SIZE);
        this.setFitHeight(POWER_UP_SIZE);
        addedPoints = pointsToAdd;
        addedLives = livesToAdd;
        addedTime = timeToAdd;
    }

    public double getSpeed() { return POWER_UP_SPEED; }

    public int addPoints() {
        return addedPoints;
    }

    public int addLives() {
        return addedLives;
    }

    public int addTime() {
        return addedTime;
    }
}

