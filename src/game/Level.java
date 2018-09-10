package game;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import java.util.ArrayList;
import java.util.Random;


import static game.Block.BLOCK_SIZE;
import static game.BracketBuster.SIZE;

public class Level extends AnchorPane {
    public static final int TARGET_SCORE = 21;
    public static final double HEIGHT = 70.0;
    public static final int NUM_BLOCK_ROWS = 3;

    private String levelName;
    private ArrayList<ArrayList<Block>> myBlockGrid;
    private int currentPositiveBlock;
    private boolean isBrickBlock;
    private int negativeBlockProbability; //out of 100
    private int currentPowerUp;
    private int powerUpProbability; //out of 100
    private GameManager myGameManager;
    private StatDisplay myStats;
    private boolean isCompleted;
    private boolean isOver;

    Level(String name, int negativeBlockProb, int powerUpProb) {
        super();
        levelName = name;
        myBlockGrid = new ArrayList<>();
        myGameManager = new GameManager();

        currentPositiveBlock = 1;

        negativeBlockProbability = negativeBlockProb;
        isBrickBlock = false;

        powerUpProbability = powerUpProb;
        currentPowerUp = 1;

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
        for(int i = 0; i < NUM_BLOCK_ROWS; i++) {
            myBlockGrid.add(new ArrayList<>());
            int end = (int) Math.floor(myScene.getWidth());
            for(int j = 0; j < end; j += BLOCK_SIZE) {
                Random rand = new Random();
                int n = rand.nextInt(100) + 1;
                boolean isNegativeBlock = n < negativeBlockProbability;
                Block blockToAdd;
                if(isNegativeBlock) {
                    if(isBrickBlock) {
                        blockToAdd = new Block(new Image(this.getClass().getClassLoader().getResourceAsStream("brick.png")),
                                0, true);
                    } else {
                        blockToAdd = new Block(new Image(this.getClass().getClassLoader().getResourceAsStream("foul.PNG")),
                                -1, false);
                    }
                    isBrickBlock = !isBrickBlock;
                } else {
                    if(currentPositiveBlock == 1) {
                        blockToAdd = new Block(new Image(this.getClass().getClassLoader().getResourceAsStream("one.PNG")),
                                1, false);
                        currentPositiveBlock++;
                    } else if(currentPositiveBlock == 2){
                        blockToAdd = new Block(new Image(this.getClass().getClassLoader().getResourceAsStream("two.PNG")),
                                2, false);
                        currentPositiveBlock++;
                    } else {
                        blockToAdd = new Block(new Image(this.getClass().getClassLoader().getResourceAsStream("three.PNG")),
                                3, false);
                        currentPositiveBlock = 1;
                    }
                }
                blockToAdd.setX(j);
                blockToAdd.setY(HEIGHT * i);
                blockToAdd.setPowerUp(powerUpProbability);
                this.getChildren().add(blockToAdd);
                myBlockGrid.get(i).add(blockToAdd);
            }
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
        PowerUp powerUp;
        if(currentPowerUp == 1) {
            powerUp = new PowerUp(new Image(this.getClass().getClassLoader().getResourceAsStream("and1.jpg")),
                    1,0,0);
            currentPowerUp++;
        } else if(currentPowerUp == 2){
            powerUp = new PowerUp(new Image(this.getClass().getClassLoader().getResourceAsStream("zion.jpg")),
                    0,1,0); //change to selected player
            currentPowerUp++;
        } else {
            powerUp = new PowerUp(new Image(this.getClass().getClassLoader().getResourceAsStream("clock.png")),
                    0,0,5);
            currentPowerUp = 1;
        }
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
