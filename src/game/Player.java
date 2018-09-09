package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double speed = 30;

    Player(Image image, int width, int height) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
    }

    public double getSpeed() { return speed; }
}
