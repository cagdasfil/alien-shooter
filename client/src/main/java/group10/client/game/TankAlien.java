package group10.client.game;

import javafx.scene.paint.Color;

public class TankAlien extends Alien {
    public TankAlien(int x, int y, int width, int height, Color color) {
        super(x, y,width,height,color);
        super.setHealth(5);
    }
}
