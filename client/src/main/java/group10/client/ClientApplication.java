package group10.client;

import group10.client.game.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

    public static void main(String[] args) {
        Application.launch(UiApplication.class, args);
    }

}
