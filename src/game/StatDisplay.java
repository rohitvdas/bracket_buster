package game;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;


public class StatDisplay extends AnchorPane {
    Text topLeft, bottomLeft, topRight, bottomRight;

    StatDisplay(double width, double height) {
        super();
        Rectangle box = new Rectangle(width, height, Paint.valueOf("BLUE"));
        topLeft = new Text(0, 0, "");
        topLeft.setFont(Font.font("Garamond", 20));
        topLeft.setFill(Color.WHITE);
        bottomLeft = new Text(0, 0, "Lives: 3");
        bottomLeft.setFont(Font.font("Garamond", 20));
        bottomLeft.setFill(Color.WHITE);
        topRight = new Text(0, 0, "Time: 60");
        topRight.setFont(Font.font("Garamond", 20));
        topRight.setFill(Color.WHITE);
        bottomRight = new Text(0, 0, "Score: ");
        bottomRight.setFont(Font.font("Garamond", 20));
        bottomRight.setFill(Color.WHITE);
        this.getChildren().addAll(box, topLeft, bottomLeft, topRight, bottomRight);
        this.setTopAnchor(topLeft, 10.0);
        this.setLeftAnchor(topLeft, 10.0);
        this.setBottomAnchor(bottomLeft, 10.0);
        this.setLeftAnchor(bottomLeft, 10.0);
        this.setTopAnchor(topRight, 10.0);
        this.setRightAnchor(topRight, 10.0);
        this.setBottomAnchor(bottomRight, 10.0);
        this.setRightAnchor(bottomRight, 10.0);
    }

    public void updateLevel(String newLevel) {
        topLeft.setText(newLevel);
    }

    public void updateLives(int newLives) {
        bottomLeft.setText("Lives: " + Integer.toString(newLives));
    }

    public void updateTime(double oldTime, double newTime) {
        if(Math.floor(oldTime) != Math.floor(newTime)) {
            topRight.setText("Time: " + (int) newTime);
        }
    }

    public void updateScore(int newScore) {
        bottomRight.setText("Score: " + Integer.toString(newScore));
    }
}
