package group10.client.game;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameStatus extends HBox {

    private Label scoreLabel;
    private Label timeLabel;
    private Label remainingHealthLabel;

    public GameStatus(int score, int time, int remainingHealth) {
        this.scoreLabel = new Label("Score: " + score);
        this.timeLabel = new Label("Time : " + time);
        this.remainingHealthLabel = new Label("Remaining Health : " + remainingHealth);

        //Setting the margin to the nodes
        this.setMargin(this.scoreLabel, new Insets(60, 60, 60, 60));
        this.setMargin(this.timeLabel, new Insets(60, 60, 60, 60));
        this.setMargin(this.remainingHealthLabel, new Insets(60, 60, 60, 60));
        configureAndAddLabels();
    }

    private void configureAndAddLabels(){
        scoreLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
        timeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
        remainingHealthLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));

        scoreLabel.setTextFill(Color.RED);
        timeLabel.setTextFill(Color.RED);
        remainingHealthLabel.setTextFill(Color.RED);

        this.getChildren().addAll(scoreLabel,timeLabel,remainingHealthLabel);
    }


    public void setScore(int score) {
        scoreLabel.setText("Score: " +  score);
    }

    public void setTime(int time) {
        timeLabel.setText("Time : " + time);
    }

    public void setRemainingHealth(int remainingHealth) {
        remainingHealthLabel.setText("Remaining Health : " + remainingHealth);
    }
}
