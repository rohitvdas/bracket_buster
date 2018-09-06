package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double speed = 30;

    Player(Image image) {
        super(image);
        this.setFitWidth(70);
        this.setFitHeight(100);
    }

    public double getSpeed() { return speed; }
}
