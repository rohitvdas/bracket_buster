package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
    private double speed = 140;
    private double directionX;
    private double directionY;

    Ball(Image image, double startingX, double startingY, double initDirectionX, double initDirectionY) {
        super(image);
        this.directionX = initDirectionX;
        this.directionY = initDirectionY;
        this.setX(startingX);
        this.setY(startingY);
        this.setFitWidth(50);
        this.setFitHeight(50);
    }

    public double getSpeed() { return speed; }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(double newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(double newDirectionY) { directionY = newDirectionY; }
}
