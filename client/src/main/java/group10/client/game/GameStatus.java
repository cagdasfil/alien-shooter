package group10.client.game;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class GameStatus extends HBox {
    // total number of kills for the player
    private Label killLabel;
    // remaining time to game end
    private Label timeLabel;
    // remaining health of the player
    private Label remainingHealthLabel;
    // total number of hits to boss for the player
    private Label hitBossLabel;
    //gameLevel
    private boolean isMultiPlayerLevel;

    /**
     *
     * @param kill total number of kill for the player
     * @param time remaining time to game end
     * @param remainingHealth remaining health of the player
     */
    public GameStatus(int kill, int time, int remainingHealth) {
        // set labels
        this.killLabel = new Label("Kills: " + kill);
        this.timeLabel = new Label("Time : " + time + " seconds");
        this.remainingHealthLabel = new Label("Remaining Health : " + remainingHealth);
        this.isMultiPlayerLevel = false;

        //Setting the margin to the nodes
        this.setMargin(this.killLabel, new Insets(60, 60, 60, 60));
        this.setMargin(this.timeLabel, new Insets(60, 60, 60, 60));
        this.setMargin(this.remainingHealthLabel, new Insets(60, 60, 60, 60));
        configureAndAddLabels();
    }

    /**
     *
     * @param hitBoss total number of kill for the player
     * @param time remaining time to game end
     * @param remainingHealth remaining health of the player
     * @param isMultiPlayerLevel flag for checking multiplayer level
     */
    public GameStatus(int hitBoss, int time, int remainingHealth,boolean isMultiPlayerLevel) {
        // set labels
        this.hitBossLabel = new Label("Hit to boss: " + hitBoss);
        this.timeLabel = new Label("Time : " + time + " seconds");
        this.remainingHealthLabel = new Label("Remaining Health : " + remainingHealth);
        this.isMultiPlayerLevel = isMultiPlayerLevel;

        //Setting the margin to the nodes
        this.setMargin(this.hitBossLabel, new Insets(50, 50, 50, 50));
        this.setMargin(this.timeLabel, new Insets(50, 50, 50, 50));
        this.setMargin(this.remainingHealthLabel, new Insets(50, 50, 50, 50));
        configureAndAddLabels();
    }

    private void configureAndAddLabels(){
        if(isMultiPlayerLevel){
            // set font for each label
            hitBossLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
            timeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
            remainingHealthLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));

            // set text color for each label
            hitBossLabel.setTextFill(Color.DARKRED);
            timeLabel.setTextFill(Color.DARKRED);
            remainingHealthLabel.setTextFill(Color.DARKRED);

            // show all labels on the screen
            this.getChildren().addAll(hitBossLabel,timeLabel,remainingHealthLabel);
        }
        else{
            // set font for each label
            killLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
            timeLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
            remainingHealthLabel.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));

            // set text color for each label
            killLabel.setTextFill(Color.RED);
            timeLabel.setTextFill(Color.RED);
            remainingHealthLabel.setTextFill(Color.RED);

            // show all labels on the screen
            this.getChildren().addAll(killLabel,timeLabel,remainingHealthLabel);
        }

    }


    /**
     * Setter method to be able to set total number of kill for the player
     * @param kill total number of kill for the player
     */
    public void setKill(int kill) {
        // set label text
        killLabel.setText("Kills: " +  kill);
    }

    /**
     * Setter method to be able to set remaining time of the game
     * @param time remaining time
     */
    public void setTime(int time) {
        // set label text
        timeLabel.setText("Time : " + time + " seconds");
    }

    /**
     * Setter method to be able to set remaining health of the player
     * @param remainingHealth remaining health of the player
     */
    public void setRemainingHealth(int remainingHealth) {
        // set label text
        remainingHealthLabel.setText("Remaining Health : " + remainingHealth);
    }


    /**
     * Setter method to be able to set total number of hits to boss for the player
     * @param hitBoss total number of hits to boss for the player
     */
    public void setHitBoss(int hitBoss) {
        // set label text
        hitBossLabel.setText("Hit to boss: " +  hitBoss);
    }


}
