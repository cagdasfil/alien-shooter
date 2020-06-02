package group10.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group10.client.api.MatchApi;
import group10.client.api.UserApi;
import group10.client.game.Game;
import group10.client.model.server.Match;
import group10.client.multiplayer.MultiplayerGame;
import group10.client.multiplayer.SocketClient;
import group10.client.multiplayer.SocketServer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * This class is controller for the WaitingRoom.fxml file
 */
@Component
public class WaitingRoomController implements Initializable {

    @FXML public Pane generalLayout;
    @FXML public Text text;
    @FXML public Button playButton;
    @FXML public ProgressIndicator progress;

    Match match;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        playButton.setVisible(false);
        try {
            match = MatchApi.getMatch();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        if(match == null){ // Server
            MatchApi.addMatch(
                    LoginController.user.getUsername()
            );
            Thread t = new Thread(() -> {
                do {
                    try {
                        match = MatchApi.getMatch();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } while (Objects.requireNonNull(match).getClientUsername().equals(""));
                playButton.setOnAction(actionEvent ->  {
                    playClickServer();
                });
                playButton.setVisible(true);
                progress.setVisible(false);
                text.setText("You are matched with " + match.getClientUsername());
            });
            t.start();
        }
        else{ // Client
            match.setClientUsername(LoginController.user.getUsername());
            MatchApi.updateMatch(match);
            text.setText("You are matched with " + match.getServerUsername());
            playButton.setOnAction(actionEvent ->  {
                playClickClient();
            });
            playButton.setVisible(true);
            progress.setVisible(false);
        }
    }

    public void playClickServer(){
        do {
            try {
                match = MatchApi.getMatch();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } while (Objects.requireNonNull(match).getClientStatus().equals("wait"));
        MultiplayerGame game = new MultiplayerGame(5, 0);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
        match.setServerStatus("ready");
        MatchApi.updateMatch(match);
    }

    public void playClickClient(){
        match.setClientStatus("ready");
        MatchApi.updateMatch(match);
        do {
            try {
                match = MatchApi.getMatch();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } while (Objects.requireNonNull(match).getServerStatus().equals("wait"));
        MultiplayerGame game = new MultiplayerGame(5, 1);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
        MatchApi.deleteMatch(match);
    }

}
