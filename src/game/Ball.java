package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Ball extends ImageView {
    public static final double OFFSET = 650; //discrepancy between image position and actual position of ball in scene

    private double directionX;
    private double directionY;

    Ball(Image image, double startingX, double startingY, double initDirectionX, double initDirectionY, double initSize) {
        super(image);
        this.directionX = initDirectionX;
        this.directionY = initDirectionY;
        this.setBallX(startingX);
        this.setBallY(startingY);
        this.setScaleX(this.getScaleX() * initSize);
        this.setScaleY(this.getScaleY() * initSize);
    }

    public double getBallX() { return this.getX() + OFFSET; }

    public void setBallX(double value) { this.setX(value - OFFSET); }

    public double getBallY() { return this.getY() + OFFSET; }

    public void setBallY(double value) { this.setY(value - OFFSET); }

    public double getDirectionX() { return directionX; }

    public void setDirectionX(int newDirectionX) { directionX = newDirectionX; }

    public double getDirectionY() { return directionY; }

    public void setDirectionY(int newDirectionY) { directionY = newDirectionY; }
}
