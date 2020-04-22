package group10.client.game;

import javafx.scene.paint.Color;

public class TankAlien extends Alien {
    public TankAlien(int x, int y, int width, int height, Color color) {
        super(width, height,width,height,color);
        setTranslateX(x);
        setTranslateY(y);
        super.setHealth(5);
    }
}
