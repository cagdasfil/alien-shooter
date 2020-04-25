package group10.client.game;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * This is base class for Aliens. We use Alien class for creating simple aliens. Simple aliens have only one health and they cannot shoot.
 */
public class Alien extends Rectangle {
    private int health;

    /**
     *
     * @param x x coordinate of the alien
     * @param y y coordinate of the alien
     * @param width width of the alien
     * @param height height of the alien
     * @param color color of the alien
     */
    public Alien(int x, int y, int width, int height, Color color) {
        // use constructor of the superclass to be able to set  width , height , color of the alien.
        super(width, height,color);
        // set x and y coordinate
        setTranslateX(x);
        setTranslateY(y);
        // set health to 1 initially
        health = 1 ;
    }

    /**
     * Getter method to be able to access the health of the alien.
     * @return remaining health of the alien
     */
    public int getHealth() {
        return health;
    }

    /**
     * Setter method to be able to set the health of the alien.
     * @param health health of the alien to be set.
     */

    public void setHealth(int health) {
        this.health =health;
    }
}
