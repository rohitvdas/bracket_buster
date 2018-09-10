package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends ImageView {
    public static final int BLOCK_SIZE = 70;

    private int numPoints;
    private boolean brickBlock;
    private boolean powerUpBlock;

    Block(Image image, double x, double y, int value, boolean brick, boolean powerUp) {
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(BLOCK_SIZE);
        this.setFitHeight(BLOCK_SIZE);
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
