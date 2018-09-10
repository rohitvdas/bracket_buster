package game;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static game.BracketBuster.SIZE;

public class Player extends ImageView {
    public static final int PLAYER_START_POSITION = SIZE/2;

    private double speed;

    Player(Image image, int width, int height, int playerSpeed) {
        super(image);
        this.setFitWidth(width);
        this.setFitHeight(height);
        this.setX(SIZE/2);
        speed = playerSpeed;
    }

    public void resetPlayer() {
        this.setX(PLAYER_START_POSITION);
    }

    public double getSpeed() { return speed; }
}
