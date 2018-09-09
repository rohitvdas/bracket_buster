package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

public class BracketBuster extends Application {
    public static final String TITLE = "Bracket Buster";
    public static final int SIZE = 700;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final String BACKGROUND_IMAGE = "cameron.jpg";
    public static final String BALL_IMAGE = "basketball.png";
    public static final String PLAYER_IMAGE = "zion.jpg";

    private AnchorPane root;
    private Scene myScene;
    private Ball myBall;
    private Player myPlayer;
    private ArrayList<ArrayList<Block>> blockGrid;
    private GameManager myGameManager;
    private StatDisplay myStats;
    private ArrayList<PowerUp> activePowerUps;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        myScene = setupGame(SIZE, SIZE);
        primaryStage.setScene(myScene);
        primaryStage.setTitle(TITLE);
        primaryStage.setResizable(false);
        primaryStage.show();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.playFromStart();

    }

    private Scene setupGame(int width, int height) {
        root = new AnchorPane();
        var backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
        ImagePattern bg = new ImagePattern(backgroundImage);
        var scene = new Scene(root, width, height, bg);

        var playerImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PLAYER_IMAGE));
        myPlayer = new Player(playerImage, 70, 100);
        root.getChildren().add(myPlayer);
        root.setBottomAnchor(myPlayer, 70.0);

        myStats = new StatDisplay(scene.getWidth(), 70.0);
        root.getChildren().add(myStats);
        root.setBottomAnchor(myStats, 0.0);

        var ballImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new Ball(ballImage, 50, 50);
        root.getChildren().add(myBall);

        this.renderBlocks(root, scene);

        activePowerUps = new ArrayList<>();

        myGameManager = new GameManager();

        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    private void step(double elapsedTime) {
        if (myBall.getBoundsInParent().intersects(myPlayer.getBoundsInParent())) {
            myBall.setDirectionX(0);
            myBall.setDirectionY(0);
            myBall.setX(myPlayer.getX());
        }

        if (myBall.getX() <= 0 || myBall.getX() + myBall.getLayoutBounds().getWidth() >= myScene.getWidth()) {
            myBall.setDirectionX(myBall.getDirectionX() * -1);
        }

        if (myBall.getY() <= 0) {
            myBall.setDirectionY(myBall.getDirectionY() * -1);
        }

        if (myBall.getY() >= myScene.getHeight()) {
            myGameManager.setLives(-1);
            myBall.resetBall();
        }

        this.handleBlockCollision();

        myBall.setX(myBall.getX() + myBall.getSpeed() * myBall.getDirectionX() * elapsedTime);
        myBall.setY(myBall.getY() + myBall.getSpeed() * myBall.getDirectionY() * elapsedTime);

        for (int i = 0; i < activePowerUps.size(); i++) {
            PowerUp currentPowerUp = activePowerUps.get(i);
            if (currentPowerUp != null) {
                currentPowerUp.setY(currentPowerUp.getY() + currentPowerUp.getSpeed() * elapsedTime);
                if(currentPowerUp.getY() >= myScene.getHeight()) {
                    activePowerUps.set(i, null);
                    root.getChildren().remove(currentPowerUp);
                }
            }
        }

        this.handlePowerUpCapture();

        if (!myGameManager.isFrozen()) {
            myStats.updateTime(myGameManager.getTimeLeft(), myGameManager.decrementTimer());
        }

        myStats.updateScore(myGameManager.getScore());
        myStats.updateLives(myGameManager.getLivesLeft());
    }

    private void renderBlocks(AnchorPane root, Scene scene) {
        blockGrid = new ArrayList<>();
        blockGrid.add(new ArrayList<>());
        blockGrid.add(new ArrayList<>());
        blockGrid.add(new ArrayList<>());

        int end = (int) Math.floor(scene.getWidth());
        for (int i = 0; i < end; i += end / 10) {
            var threeBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("three.PNG"));
            Block threeBlock = new Block(threeBlockImage, 70, 70, i, 0, 3, false, false);
            root.getChildren().add(threeBlock);
            blockGrid.get(0).add(threeBlock);

            var twoBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("two.PNG"));
            Block twoBlock = new Block(twoBlockImage, 70, 70, i, 70, 2, false, false);
            root.getChildren().add(twoBlock);
            blockGrid.get(1).add(twoBlock);

            var oneBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("one.PNG"));
            Block oneBlock = new Block(oneBlockImage, 70, 70, i, 140, 1, false, true);
            root.getChildren().add(oneBlock);
            blockGrid.get(2).add(oneBlock);
        }
    }

    private void handleMouseInput(double mouseX, double mouseY) {
        if (myBall.getBoundsInParent().intersects(myPlayer.getBoundsInParent())) {
            double xVector = mouseX - myBall.getX();
            double yVector = mouseY - myBall.getY();
            double magnitude = Math.sqrt(xVector * xVector + yVector * yVector);
            myBall.setY(myBall.getY() - 10);
            myBall.setDirectionX(xVector / magnitude);
            myBall.setDirectionY(yVector / magnitude);
        }
    }

    private void handleKeyInput(KeyCode code) {
        if (code == KeyCode.RIGHT && myPlayer.getX() + myPlayer.getLayoutBounds().getWidth() < myScene.getWidth()) {
            myPlayer.setX(myPlayer.getX() + myPlayer.getSpeed());
        }
        if (code == KeyCode.LEFT && myPlayer.getX() > 0) {
            myPlayer.setX(myPlayer.getX() - myPlayer.getSpeed());
        }
        if (code == KeyCode.H) {
            myGameManager.setScore(1);
        }
        if (code == KeyCode.DIGIT5) {
            if (!myGameManager.isFrozen()) {
                myGameManager.freezeTime();
            } else {
                myGameManager.unfreezeTime();
            }
        }
    }

    private void handleBlockCollision() {
        for (int i = 0; i < blockGrid.size(); i++) {
            for (int j = 0; j < blockGrid.get(0).size(); j++) {
                Block currentBlock = blockGrid.get(i).get(j);
                if (currentBlock != null && myBall.getBoundsInParent().intersects(currentBlock.getBoundsInParent())) {
                    if (currentBlock.isBrick()) {
                        myBall.setDirectionX(0);
                        myBall.setDirectionY(1);
                    } else if (myBall.getX() + myBall.getLayoutBounds().getWidth() >= currentBlock.getX() ||
                            myBall.getX() <= currentBlock.getX() + currentBlock.getLayoutBounds().getWidth()) {
                        myBall.setDirectionX(myBall.getDirectionX() * -1);
                        myBall.setDirectionY(1);
                    } else if (myBall.getY() + myBall.getLayoutBounds().getHeight() >= currentBlock.getY() ||
                            myBall.getY() <= currentBlock.getY() + currentBlock.getLayoutBounds().getHeight()) {
                        myBall.setDirectionY(myBall.getDirectionY() * -1);
                    }
                    if (currentBlock.containsPowerUp()) {
                        this.dropPowerUp(currentBlock.getX(), currentBlock.getY());
                    }
                    blockGrid.get(i).set(j, null);
                    root.getChildren().remove(currentBlock);
                    myGameManager.setScore(currentBlock.getValue());
                }
            }
        }
    }

    private void dropPowerUp(double x, double y) {
        var powerUpImage = new Image(this.getClass().getClassLoader().getResourceAsStream("and1.jpg"));
        PowerUp powerUp = new PowerUp(powerUpImage, 50, 50, 1, 0, 0);
        powerUp.setX(x);
        powerUp.setY(y);
        root.getChildren().add(powerUp);
        activePowerUps.add(powerUp);
    }

    private void handlePowerUpCapture() {
        for (int i = 0; i < activePowerUps.size(); i++) {
            PowerUp currentPowerUp = activePowerUps.get(i);
            if (currentPowerUp != null && myPlayer.getBoundsInParent().intersects(currentPowerUp.getBoundsInParent())) {
                activePowerUps.set(i, null);
                root.getChildren().remove(currentPowerUp);
                myGameManager.setScore(currentPowerUp.addPoints());
                myGameManager.setLives(currentPowerUp.addLives());
                myGameManager.incrementTimer(currentPowerUp.addTime());
            }
        }
    }
}
