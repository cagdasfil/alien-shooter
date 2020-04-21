package group10.client.game;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;


public class Player extends Rectangle {
    private boolean isAlive;
    private int health;
    private Image image;

    public Player(int x, int y, int width, int height,Color color,int health) {
        super(width, height,color);
        setTranslateX(x);
        setTranslateY(y);
        isAlive = true;
        this.health = health;
        image = new javafx.scene.image.Image("spaceShip.png");
        ImagePattern imagePattern = new ImagePattern(image);
        this.setFill(imagePattern);
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
