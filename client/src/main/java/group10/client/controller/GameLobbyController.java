package group10.client.controller;

import group10.client.api.ScoreApi;
import group10.client.game.Game;
import group10.client.model.server.Score;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class GameLobbyController implements Initializable {

    @FXML public Pane generalLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void playClick() throws IOException {
        Game game = new Game(1);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
    }

    @FXML
    public void leaderboardClick() throws IOException {
        Parent leaderboard = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Leaderboard.fxml")));
        generalLayout.getChildren().setAll(leaderboard);
    }

    @FXML
    public void quitClick() throws IOException {
        LoginController.user = null;
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        generalLayout.getChildren().setAll(login);
    }

}
