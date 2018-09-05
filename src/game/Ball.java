package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
    private double directionX;
    private double directionY;

    Ball(Image image, double startingX, double startingY, double initDirectionX, double initDirectionY, double initSize) {
        super(image);
        this.directionX = initDirectionX;
        this.directionY = initDirectionY;
        this.setX(startingX);
        this.setY(startingY);
        this.setScaleX(this.getScaleX() * initSize);
        this.setScaleY(this.getScaleY() * initSize);
    }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(int newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(int newDirectionY) { directionY = newDirectionY; }
}
