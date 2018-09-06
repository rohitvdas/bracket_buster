package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

public class BracketBuster extends Application {
    public static final String TITLE = "Bracket Buster";
    public static final int SIZE = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String BACKGROUND_IMAGE = "cameron.jpg";
    public static final String BALL_IMAGE = "basketball.png";
    public static final String PLAYER_IMAGE = "zion.jpg";

    private Scene myScene;
    private Ball myBall;
    private Player myPlayer;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        myScene = setupGame(SIZE, SIZE);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.show();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();

    }

    private Scene setupGame(int width, int height) {
        var root = new AnchorPane();
        var backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
        ImagePattern bg = new ImagePattern(backgroundImage);
        var scene = new Scene(root, width, height, bg);

        var ballImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new Ball(ballImage, 0, 0, 1, 1);
        root.getChildren().add(myBall);

        var playerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PLAYER_IMAGE));
        myPlayer = new Player(playerImage);
        root.getChildren().add(myPlayer);
        root.setBottomAnchor(myPlayer, 0.0);

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void step(double elapsedTime) {
        if(myBall.getBoundsInParent().intersects(myPlayer.getBoundsInParent())) {
            myBall.setDirectionX(0);
            myBall.setDirectionY(0);
            myBall.setX(myPlayer.getX());
        }
        myBall.setX(myBall.getX() + myBall.getSpeed() * myBall.getDirectionX() * elapsedTime);
        myBall.setY(myBall.getY() + myBall.getSpeed() * myBall.getDirectionY() * elapsedTime);
    }

    private void handleMouseInput(double x, double y) {
    }

    private void handleKeyInput(KeyCode code) {
        if(code == KeyCode.RIGHT) {
            myPlayer.setX(myPlayer.getX() + myPlayer.getSpeed());
        } else if(code == KeyCode.LEFT) {
            myPlayer.setX(myPlayer.getX() - myPlayer.getSpeed());
        }
    }
}
