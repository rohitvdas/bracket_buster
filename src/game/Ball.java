package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static game.BracketBuster.SIZE;

public class Ball extends ImageView {
    public static final int SPEED = 170;
    public static final double STARTING_X = SIZE/2;
    public static final double STARTING_Y = SIZE/2;
    public static final double INIT_DIRECTION_X = 0;
    public static final double INIT_DIRECTION_Y = 1;

    private double directionX;
    private double directionY;

    Ball(Image image, int width, int height) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        resetBall();
    }

    public void resetBall() {
        this.directionX = INIT_DIRECTION_X;
        this.directionY = INIT_DIRECTION_Y;
        this.setX(STARTING_X);
        this.setY(STARTING_Y);
    }

    public double getSpeed() { return SPEED; }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(double newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(double newDirectionY) { directionY = newDirectionY; }
}
