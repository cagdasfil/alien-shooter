package group10.client.controller;

import group10.client.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
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
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Exit");
        alert.setHeaderText("Are you sure you want to exit ?");
        alert.setContentText(null);

        ButtonType buttonTypeYes = new ButtonType("Yes");
        ButtonType buttonTypeNo = new ButtonType("No");

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes){
            LoginController.user = null;
            Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
            generalLayout.getChildren().setAll(login);
        }
    }

}
