package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends ImageView {
    private int numPoints;
    private boolean brickBlock;

    Block(Image image, int width, int height, double x, double y, int value, boolean brick) {
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        numPoints = value;
        brickBlock = brick;
    }

    public int getValue() {
        return numPoints;
    }

    public boolean isBrick() {
        return brickBlock;
    }

}
