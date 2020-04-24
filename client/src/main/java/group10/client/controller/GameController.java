package group10.client.controller;

import group10.client.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class GameController implements Initializable {

    @FXML public AnchorPane gameLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Game game = new Game(1);
        gameLayout.getChildren().setAll(game);
    }
}
