package group10.client.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class SignUpController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
