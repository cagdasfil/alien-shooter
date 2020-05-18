package group10.client.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * This class for our spaceship that shoot and kill the enemies.
 */

public class Player extends Rectangle {
    private int health;
    private Image image;
    private int kills;
    private int score;

    /**
     * Constructor for Player Class
     * @param x x coordinate of the player
     * @param y y coordinate of the player
     * @param width width of the player
     * @param height height of the player
     * @param color color of the player
     * @param health health of the player
     */
    public Player(int x, int y, int width, int height,Color color,int health) {
        // constructor of the super class to be able to set width, height and color of the player
        super(width, height,color);
        // set x and y coordinate of the player
        setTranslateX(x);
        setTranslateY(y);

        this.health = health;
        // set number of kills and score of the player zero initially.
        this.kills = 0;
        this.score = 0;

        // Set spaceship image
        //image = new javafx.scene.image.Image("static/spaceship.png");
        //ImagePattern imagePattern = new ImagePattern(image);
        //this.setFill(imagePattern);
    }

    /**
     * Getter method to be able to access health of the player
     * @return health of the player
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter method to be able to set health of the player
     * @param health health of the player
     */

    public void setHealth(int health) {
        this.health = health;
    }

    /**
     * Getter method to be able to access number of kills of the player
     * @return number of kills of the player
     */

    public int getKills() {
        return kills;
    }

    /**
     * Setter method to be able to set number of kills of the player
     * @param kills number of kills of the player
     */
    public void setKills(int kills) {
        this.kills = kills;
    }

    /**
     * Getter method to be able to access score of the player
     * @return score of the player
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter method to be able to set score of the player
     * @param score score of the player
     */
    public void setScore(int score) {
        this.score = score;
    }
}
