package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
    private double speed = 170;
    private double directionX;
    private double directionY;

    Ball(Image image, int width, int height, double startingX, double startingY, double initDirectionX, double initDirectionY) {
        super(image);
        this.directionX = initDirectionX;
        this.directionY = initDirectionY;
        this.setX(startingX);
        this.setY(startingY);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public double getSpeed() { return speed; }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(double newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(double newDirectionY) { directionY = newDirectionY; }
}
