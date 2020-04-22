package group10.client.controller;

import group10.client.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    @FXML public AnchorPane generalLayout;
    @FXML public Label label;
    @FXML public Button button;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void buttonClick() throws IOException {
        Parent signUp = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SignUp.fxml")));
        generalLayout.getChildren().setAll(signUp);
    }

    @FXML
    public void playbuttonClick() throws IOException {
        Game game = new Game();
        generalLayout.getChildren().setAll(game);
    }

}
