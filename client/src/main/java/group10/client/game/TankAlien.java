package group10.client.game;

import javafx.scene.paint.Color;

/**
 * This is class for the tank aliens. They should be hit more than one time to be destroyed.
 * We get health setting from GameTuner class for the Game.
 */

public class TankAlien extends Alien {
    public TankAlien(int x, int y, int width, int height, Color color, int health) {
        // use constructor of the superclass to be able to set x coordinate, y coordinate, width , height , color of the tank alien.
        super(x, y,width,height,color);
        // set health
        super.setHealth(health);
    }
}
