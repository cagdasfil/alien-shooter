package group10.client.controller;

import group10.client.game.Game;
import group10.client.multiplayer.MultiplayerGame;
import group10.client.multiplayer.SocketServer;
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

/**
 * This class is controller for the GameLobby.fxml file
 */
@Component
public class WaitingRoomController implements Initializable {

    @FXML public Pane generalLayout;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    public void readyClick() throws IOException {
        SocketServer socketServer = null;
        try {
            socketServer = new SocketServer();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        MultiplayerGame game = new MultiplayerGame(5, socketServer);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
    }

}
