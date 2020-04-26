package group10.client.controller;

import group10.client.api.UserApi;
import group10.client.model.server.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is controller for the Login.fxml file
 */
@Component
public class LoginController implements Initializable {

    public static User user;
    public static String apiAddress;
    @Value("${spring.application.apiAddress}") private String applicationApiAddress;

    @FXML public AnchorPane generalLayout;
    @FXML public Button loginButton;
    @FXML public Button signUpButton;
    @FXML public TextField usernameField;
    @FXML public TextField passwordField;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(applicationApiAddress != null){
            apiAddress = applicationApiAddress;
        }
    }

    /**
     * Controller function of the sign up button click
     * @throws IOException exception for FXML load operation
     */
    @FXML
    public void signUpClick() throws IOException {
        Parent signUp = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("SignUp.fxml")));
        generalLayout.getChildren().setAll(signUp);
    }

    /**
     * Controller function of the login button click
     * @throws IOException exception for FXML load operation
     */
    @FXML
    public void loginClick() throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();

        if(isFormValid()) {
            String response = UserApi.loginUser(username, password);
            if (response.contains("error")) {
                Alert badAuthAlert = new Alert(Alert.AlertType.ERROR);
                badAuthAlert.setTitle("Authentication Error");
                badAuthAlert.setHeaderText("Wrong username or password !");
                badAuthAlert.showAndWait();
            } else {
                user = UserApi.getUser(username);
                Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
                generalLayout.getChildren().setAll(gameLobby);
            }
        }
        else{
            Alert invalidFormAlert = new Alert(Alert.AlertType.ERROR);
            invalidFormAlert.setTitle("Validation Error");
            invalidFormAlert.setHeaderText("Please provide username and password !");
            invalidFormAlert.showAndWait();
        }

    }

    /**
     * This method checks if username and password are provided
     * @return boolean value of values are provided or not
     */
    public boolean isFormValid(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        return !username.isEmpty() && !password.isEmpty();
    }

}
