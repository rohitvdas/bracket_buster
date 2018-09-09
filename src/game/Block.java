package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends ImageView {
    private int numPoints;
    private boolean brickBlock;
    private boolean powerUpBlock;

    Block(Image image, int width, int height, double x, double y, int value, boolean brick, boolean powerUp) {
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        numPoints = value;
        brickBlock = brick;
        powerUpBlock = powerUp;
    }

    public int getValue() {
        return numPoints;
    }

    public boolean isBrick() {
        return brickBlock;
    }

    public boolean containsPowerUp() {
        return powerUpBlock;
    }

}
