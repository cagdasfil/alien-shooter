package group10.client.controller;

import group10.client.game.Game;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

@Component
public class LoginController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    @FXML public AnchorPane generalLayout;
    @FXML public Button loginButton;
    @FXML public Button signUpButton;
    @FXML public TextField usernameField;
    @FXML public TextField passwordField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    @FXML
    public void signUpClick() throws IOException {
        Parent signUp = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SignUp.fxml")));
        generalLayout.getChildren().setAll(signUp);
    }

    @FXML
    public void loginClick() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",username);
        map.add("password",password);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        String response = restTemplate.exchange(apiAddress + "/login",
                            HttpMethod.POST,
                            entity,
                            String.class).toString();

        if (response.contains("error")){
            Alert badAuthAlert = new Alert(Alert.AlertType.ERROR);
            badAuthAlert.setTitle("Authentication Error");
            badAuthAlert.setHeaderText("Wrong username or password !");
            badAuthAlert.showAndWait();
        }
        else {
            Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
            generalLayout.getChildren().setAll(gameLobby);
        }

    }

}
