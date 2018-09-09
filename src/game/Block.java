package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Block extends ImageView {
    private int numPoints;
    private boolean isBrick;

    Block(Image image, int width, int height, double x, double y, int value) {
        super(image);
        this.setX(x);
        this.setY(y);
        this.setFitWidth(width);
        this.setFitHeight(height);
        numPoints = value;
    }

    public int getValue() {
        return numPoints;
    }

}
