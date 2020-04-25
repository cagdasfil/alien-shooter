package group10.client.game;

import javafx.scene.paint.Color;

/**
 * This is class for the aliens having ability to shoot.
 */
public class ShooterAlien extends Alien {
    public ShooterAlien(int x, int y, int width, int height, Color color) {
        // use constructor of the superclass to be able to set x coordinate, y coordinate, width , height , color of the shooter alien.
        super(x, y,width,height,color);
    }
}
