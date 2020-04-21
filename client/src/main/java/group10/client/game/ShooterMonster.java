package group10.client.game;

import javafx.scene.paint.Color;

public class ShooterMonster extends  Monster{
    public ShooterMonster(int x, int y, int width, int height, Color color) {
        super(width, height,width,height,color);
        setTranslateX(x);
        setTranslateY(y);
    }
}
