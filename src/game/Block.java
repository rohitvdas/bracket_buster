package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Random;

public class Block extends ImageView {
    public static final int BLOCK_SIZE = 70;

    private int numPoints;
    private boolean brickBlock;
    private boolean powerUpBlock;

    Block(Image image, int value, boolean brick) {
        super(image);
        this.setFitWidth(BLOCK_SIZE);
        this.setFitHeight(BLOCK_SIZE);
        numPoints = value;
        brickBlock = brick;
        powerUpBlock = false;
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

    public void setPowerUp(int powerUpProbability) {
        Random rand = new Random();
        int n = rand.nextInt(100) + 1;
        powerUpBlock = n < powerUpProbability;
    }

}
