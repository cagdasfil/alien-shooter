package group10.client.multiplayer;

import group10.client.game.Alien;
import javafx.scene.paint.Color;

public class Boss extends Alien {
    public Boss(int x, int y, int width, int height, Color color,int health) {
        super(x, y, width, height, color);
        super.setHealth(health);
    }
}
