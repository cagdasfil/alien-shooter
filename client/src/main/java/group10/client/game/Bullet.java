package group10.client.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This is class for Bullets.
 */
public class Bullet extends Circle {
    /**
     *
     * @param x x coordinate of the bullet
     * @param y y coordinate of the bullet
     * @param radius radius of the bullet
     * @param color color of the bullet
     */
    public Bullet(int x, int y, int radius, Color color) {
        // use constructor of the superclass to be able to set  radius and color of the bullet object
        super(radius,color);
        // set x and y coordinate of the bullet
        setTranslateX(x);
        setTranslateY(y);
    }
}
