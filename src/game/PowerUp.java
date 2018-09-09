package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PowerUp extends ImageView {
    private double speed = 100;
    private int addedPoints;
    private int addedLives;
    private int addedTime;

    PowerUp(Image image, int width, int height, int pointsToAdd, int livesToAdd, int timeToAdd) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        addedPoints = pointsToAdd;
        addedLives = livesToAdd;
        addedTime = timeToAdd;
    }

    public double getSpeed() { return speed; }

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

