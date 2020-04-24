package group10.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
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
public class SignUpController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    @FXML public AnchorPane generalLayout;
    @FXML public TextField usernameField;
    @FXML public TextField passwordField;
    @FXML public TextField nameField;
    @FXML public TextField surnameField;
    @FXML public TextField emailField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        restTemplate = new RestTemplate();
    }

    @FXML
    public void signUpClick() throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();

        String jsonString = new JSONObject()
                .put("username", username)
                .put("password", password)
                .put("name", name)
                .put("surname", surname)
                .put("email", email).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        ResponseEntity<JSONObject> response = restTemplate.exchange(apiAddress + "/sign_up",
                HttpMethod.POST,
                entity,
                JSONObject.class);

        System.out.println(response);

        if (response.getStatusCode().is2xxSuccessful()){
            Alert successfulSignUpAlert = new Alert(Alert.AlertType.CONFIRMATION);
            successfulSignUpAlert.setTitle("Successful Sign Up");
            successfulSignUpAlert.setHeaderText("Account has been created successfully !");
            successfulSignUpAlert.showAndWait();
            Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
            generalLayout.getChildren().setAll(login);
        }
        else{
            Alert failedSignUpAlert = new Alert(Alert.AlertType.ERROR);
            failedSignUpAlert.setTitle("Sign Up Error");
            failedSignUpAlert.setHeaderText("Sign up operation has been failed !");
            failedSignUpAlert.showAndWait();
        }

    }

    @FXML
    public void backClick() throws IOException {
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        generalLayout.getChildren().setAll(login);
    }
}
