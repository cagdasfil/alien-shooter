package group10.client.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Alien extends Rectangle {
    private boolean isAlive;
    private int health;

    public Alien(int x, int y, int width, int height, Color color) {
        super(width, height,color);
        setTranslateX(x);
        setTranslateY(y);
        isAlive = true;
        health = 1 ;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health =health;
    }
}
