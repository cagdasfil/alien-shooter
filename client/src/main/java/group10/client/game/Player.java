package group10.client.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Player extends Rectangle {
    private boolean isAlive;
    private int health;

    public Player(int x, int y, int width, int height,Color color,int health) {
        super(width, height,color);
        setTranslateX(x);
        setTranslateY(y);
        isAlive = true;
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
