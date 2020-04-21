package group10.client.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bullet extends Circle {
    private boolean isAlive;

    public Bullet(int x, int y, int radius, Color color) {
        super(radius,color);
        setTranslateX(x);
        setTranslateY(y);
        isAlive = true;
    }
}
