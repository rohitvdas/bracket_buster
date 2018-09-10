package game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
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

    private Stage stage;
    private Level root;
    private Scene myScene;
    private Timeline animation;
    private Ball myBall;
    private Player myPlayer;
    private ArrayList<Level> levelList;
    private int currentLevel = 0;
    private ArrayList<PowerUp> activePowerUps;
    private boolean isSplashScreen;
    private boolean isGameWon;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        levelList = new ArrayList<>();
        isGameWon = false;
        createLevelList(levelList);
        stage = primaryStage;
        myScene = setupSplashScreen();
        stage.setScene(myScene);
        isSplashScreen = true;
        stage.setTitle(TITLE);
        stage.setResizable(false);
        stage.show();
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.playFromStart();
    }

    private void createLevelList(ArrayList<Level> list) {
        list.add(new Level("Sweet Sixteen", 10, 40));
        list.add(new Level("Elite Eight", 20, 30));
        list.add(new Level("Final Four", 30, 20));
        list.add(new Level("National Championship", 40, 10));
    }

    private Scene setupSplashScreen() {
        AnchorPane splashScreen = new AnchorPane();
        var scene = new Scene(splashScreen, SIZE, SIZE, Paint.valueOf("BLUE"));

        var playerImage = new Image(this.getClass().getClassLoader().getResourceAsStream("zion.jpg"));
        myPlayer = new Player(playerImage, 70, 100, 30);

        var ballImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BALL_IMAGE));
        myBall = new Ball(ballImage, 50, 50);

        scene.setOnMouseClicked(e -> handleSplashScreenMouseInput(e.getX(), e.getY()));

        return scene;
    }

    private void step(double elapsedTime) {
        if(!isSplashScreen) {
            root.checkIfCompleted();
            if(root.isLevelCompleted()) {
                if(currentLevel < levelList.size() - 1) {
                    root = levelList.get(++currentLevel);
                    switchLevel(root);
                } else {
                    isGameWon = true;
                    endGame();
                }
            }

            root.checkIfGameOver();
            if(root.isGameOver()) {
                endGame();
            }

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
                root.subtractLife();
                myBall.resetBall();
            }

            root.handleBlockCollision(myBall, activePowerUps);

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

            root.handlePowerUpCapture(myPlayer, activePowerUps);
            root.checkIfFrozen();
            root.updateStats();
        }
    }

    private void handleSplashScreenMouseInput(double mouseX, double mouseY) {
        root = levelList.get(0);
        switchLevel(root);
    }

    private void switchLevel(Level nextLevel) {
        isSplashScreen = false;
        var backgroundImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BACKGROUND_IMAGE));
        ImagePattern bg = new ImagePattern(backgroundImage);
        myScene = new Scene(nextLevel, SIZE, SIZE, bg);

        nextLevel.getChildren().add(myPlayer);
        nextLevel.setBottomAnchor(myPlayer, 70.0);

        StatDisplay statDisplay = nextLevel.getStatDisplay();
        nextLevel.getChildren().add(statDisplay);
        nextLevel.setBottomAnchor(statDisplay, 0.0);

        nextLevel.getChildren().add(myBall);

        activePowerUps = new ArrayList<>();

        nextLevel.renderBlocks(myScene);

        myScene.setOnKeyPressed(e -> nextLevel.handleKeyInput(e.getCode(), myScene, myPlayer));
        myScene.setOnMouseClicked(e -> nextLevel.handleMouseInput(e.getX(), e.getY(), myBall, myPlayer));

        stage.setScene(myScene);
        myBall.resetBall();
        myPlayer.resetPlayer();
    }

    private void endGame() {
        if(isGameWon) {
            AnchorPane winningScreen = new AnchorPane();
            myScene = new Scene(winningScreen, SIZE, SIZE, Paint.valueOf("WHITE"));
        } else {
            AnchorPane losingScreen = new AnchorPane();
            myScene = new Scene(losingScreen, SIZE, SIZE, Paint.valueOf("BLACK"));
        }
        stage.setScene(myScene);
        //add play again button and write handler method to restart animation
    }
}
