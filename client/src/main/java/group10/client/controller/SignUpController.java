package group10.client.controller;

import group10.client.api.UserApi;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is controller for the SignUp.fxml file
 */
@Component
public class SignUpController implements Initializable {

    @FXML public AnchorPane generalLayout;
    @FXML public TextField usernameField;
    @FXML public TextField passwordField;
    @FXML public TextField nameField;
    @FXML public TextField surnameField;
    @FXML public TextField emailField;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    /**
     * Controller function of the submit button click
     * @throws IOException exception for FXML load operation
     */
    @FXML
    public void submitClick() throws IOException {

        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();

        if(isFormValid()){
            ResponseEntity<JSONObject> response = UserApi.signUpUser(
                    username, password, name, surname, email
            );
            if (response.getStatusCode().is2xxSuccessful()){
                Alert successfulSignUpAlert = new Alert(Alert.AlertType.INFORMATION);
                successfulSignUpAlert.setTitle("Successful Sign Up");
                successfulSignUpAlert.setHeaderText("Account has been created successfully !");
                successfulSignUpAlert.setContentText("Redirecting to login page...");
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
        else{
            Alert invalidFormAlert = new Alert(Alert.AlertType.ERROR);
            invalidFormAlert.setTitle("Validation Error");
            invalidFormAlert.setHeaderText("Invalid form values !");
            invalidFormAlert.showAndWait();
        }
    }

    /**
     * Controller function of the back link click
     * @throws IOException exception for FXML load operation
     */
    @FXML
    public void backClick() throws IOException {
        Parent login = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("Login.fxml")));
        generalLayout.getChildren().setAll(login);
    }

    /**
     * This method checks if all user information is provided
     * and e-mail contains "@" character.
     * @return boolean value of values are provided or not
     */
    public boolean isFormValid(){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String name = nameField.getText();
        String surname = surnameField.getText();
        String email = emailField.getText();

        return !username.isEmpty() &&
                !password.isEmpty() &&
                !name.isEmpty() &&
                !surname.isEmpty() &&
                !email.isEmpty() &&
                email.contains("@");
    }

}
