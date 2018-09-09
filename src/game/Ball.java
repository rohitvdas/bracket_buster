package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
    private double speed = 170;
    private double directionX;
    private double directionY;
    private double startingX = 350;
    private double startingY = 350;
    private double initDirectionX = -1;
    private double initDirectionY = 1;

    Ball(Image image, int width, int height) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        resetBall();
    }

    public void resetBall() {
        this.directionX = initDirectionX;
        this.directionY = initDirectionY;
        this.setX(startingX);
        this.setY(startingY);
    }

    public double getSpeed() { return speed; }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(double newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(double newDirectionY) { directionY = newDirectionY; }
}
