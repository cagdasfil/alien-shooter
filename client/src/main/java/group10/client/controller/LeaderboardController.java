package group10.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    @FXML public AnchorPane generalLayout;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        restTemplate = new RestTemplate();
    }

    @FXML
    public void backClick() throws IOException {
        Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
        generalLayout.getChildren().setAll(gameLobby);
    }

}
