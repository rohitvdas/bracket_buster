package game;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import static game.BracketBuster.SIZE;

public class Level extends AnchorPane {
    public static final int TARGET_SCORE = 21;
    public static final double HEIGHT = 70.0;

    private String levelName;
    private ArrayList<ArrayList<Block>> myBlockGrid;
    private GameManager myGameManager;
    private StatDisplay myStats;
    private boolean isCompleted;
    private boolean isOver;

    Level(String name) {
        super();
        levelName = name;
        myBlockGrid = new ArrayList<>();
        myGameManager = new GameManager();
        myStats = new StatDisplay(SIZE, HEIGHT, levelName);
        isCompleted = false;
        isOver = false;
    }

    public StatDisplay getStatDisplay() {
        return myStats;
    }

    public void subtractLife() {
        myGameManager.setLives(-1);
    }

    public void checkIfFrozen() {
        if (!myGameManager.isFrozen()) {
            myStats.updateTime(myGameManager.getTimeLeft(), myGameManager.decrementTimer());
        }
    }

    public void checkIfCompleted() {
        if(myGameManager.getScore() >= TARGET_SCORE) {
            isCompleted = true;
        }
    }

    public void checkIfGameOver() {
        if(myGameManager.getLivesLeft() <= 0 || myGameManager.getTimeLeft() <= 0) {
            isOver = true;
        }
    }

    public void updateStats() {
        myStats.updateScore(myGameManager.getScore());
        myStats.updateLives(myGameManager.getLivesLeft());
    }

    public boolean isLevelCompleted() {
        return isCompleted;
    }

    public boolean isGameOver() {
        return isOver;
    }

    public void renderBlocks(Scene myScene) {
        myBlockGrid.add(new ArrayList<>());
        myBlockGrid.add(new ArrayList<>());
        myBlockGrid.add(new ArrayList<>());

        int end = (int) Math.floor(myScene.getWidth());
        for (int i = 0; i < end; i += end / 10) {
            var threeBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("three.PNG"));
            Block threeBlock = new Block(threeBlockImage, i, 0, 3, false, false);
            this.getChildren().add(threeBlock);
            myBlockGrid.get(0).add(threeBlock);

            var twoBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("two.PNG"));
            Block twoBlock = new Block(twoBlockImage, i, 70, 2, false, false);
            this.getChildren().add(twoBlock);
            myBlockGrid.get(1).add(twoBlock);

            var oneBlockImage = new Image(this.getClass().getClassLoader().getResourceAsStream("one.PNG"));
            Block oneBlock = new Block(oneBlockImage, i, 140, 1, false, true);
            this.getChildren().add(oneBlock);
            myBlockGrid.get(2).add(oneBlock);
        }
    }

    public void handleMouseInput(double mouseX, double mouseY, Ball myBall, Player myPlayer) {
        if (myBall.getBoundsInParent().intersects(myPlayer.getBoundsInParent())) {
            double xVector = mouseX - myBall.getX();
            double yVector = mouseY - myBall.getY();
            double magnitude = Math.sqrt(xVector * xVector + yVector * yVector);
            myBall.setY(myBall.getY() - 10);
            myBall.setDirectionX(xVector / magnitude);
            myBall.setDirectionY(yVector / magnitude);
        }
    }

    public void handleKeyInput(KeyCode code, Scene myScene, Player myPlayer) {
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
        if (code == KeyCode.K) {
            isCompleted = true;
        }
    }

    public void handleBlockCollision(Ball myBall, ArrayList<PowerUp> myActivePowerUps) {
        for (int i = 0; i < myBlockGrid.size(); i++) {
            for (int j = 0; j < myBlockGrid.get(0).size(); j++) {
                Block currentBlock = myBlockGrid.get(i).get(j);
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
                        this.dropPowerUp(currentBlock.getX(), currentBlock.getY(), myActivePowerUps);
                    }
                    myBlockGrid.get(i).set(j, null);
                    this.getChildren().remove(currentBlock);
                    myGameManager.setScore(currentBlock.getValue());
                }
            }
        }
    }

    private void dropPowerUp(double x, double y, ArrayList<PowerUp> myActivePowerUps) {
        var powerUpImage = new Image(this.getClass().getClassLoader().getResourceAsStream("and1.jpg"));
        PowerUp powerUp = new PowerUp(powerUpImage,1, 0, 0);
        powerUp.setX(x);
        powerUp.setY(y);
        this.getChildren().add(powerUp);
        myActivePowerUps.add(powerUp);
    }

    public void handlePowerUpCapture(Player myPlayer, ArrayList<PowerUp> myActivePowerUps) {
        for (int i = 0; i < myActivePowerUps.size(); i++) {
            PowerUp currentPowerUp = myActivePowerUps.get(i);
            if (currentPowerUp != null && myPlayer.getBoundsInParent().intersects(currentPowerUp.getBoundsInParent())) {
                myActivePowerUps.set(i, null);
                this.getChildren().remove(currentPowerUp);
                myGameManager.setScore(currentPowerUp.addPoints());
                myGameManager.setLives(currentPowerUp.addLives());
                myGameManager.incrementTimer(currentPowerUp.addTime());
            }
        }
    }
}
