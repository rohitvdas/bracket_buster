package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Player extends ImageView {
    private double directionX;

    Player(Image image) {
        super(image);
        this.setScaleX(this.getScaleX() * 0.3);
        this.setScaleY(this.getScaleY() * 0.3);
    }

}
