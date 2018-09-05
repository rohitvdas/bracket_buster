package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double directionX;
    private double speed = 30;

    Player(Image image) {
        super(image);
        this.setScaleX(this.getScaleX() * 0.3);
        this.setScaleY(this.getScaleY() * 0.3);
    }

    public double getSpeed() { return speed; }
}
