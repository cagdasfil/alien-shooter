package group10.client.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Player extends Rectangle {
    private int health;
    private Image image;
    private int kills;
    private int score;

    public Player(int x, int y, int width, int height,Color color,int health) {
        super(width, height,color);
        setTranslateX(x);
        setTranslateY(y);

        this.health = health;
        this.kills = 0;
        this.score = 0;

        image = new javafx.scene.image.Image("static/spaceShip.png");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
