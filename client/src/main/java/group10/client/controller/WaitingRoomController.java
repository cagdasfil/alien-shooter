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
 * This class is controller for the GameLobby.fxml file
 */
@Component
public class WaitingRoomController implements Initializable {

    @FXML public Pane generalLayout;
    @FXML public Text readyText;
    @FXML public Button playButton;

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
                } while (Objects.requireNonNull(match).getClient_player().equals(""));
                playButton.setOnAction(actionEvent ->  {
                    playClickServer();
                });
                playButton.setVisible(true);
                readyText.setText("You are matched with " + match.getClient_player());
            });
            t.start();
        }
        else{ // Client
            match.setClient_player(LoginController.user.getUsername());
            MatchApi.updateMatch(match);
            readyText.setText("You are matched with " + match.getServer_player());
            Thread t = new Thread(() -> {
                do {
                    try {
                        match = MatchApi.getMatch();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } while (Objects.requireNonNull(match).getStatus().equals("wait"));
                playButton.setOnAction(actionEvent ->  {
                    playClickClient();
                });
                playButton.setVisible(true);
            });
            t.start();
            /*
            Thread t = new Thread(() -> {
                do {
                    try {
                        match = MatchApi.getMatch();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } while (Objects.requireNonNull(match).getStatus().equals("wait"));
                playButton.setOnAction(actionEvent ->  {
                    playClickClient();
                });
                playButton.fire();
            });
            t.start();
            //MatchApi.deleteMatch(match);

             */
        }

    }

    @FXML
    public void readyClick() throws IOException, InterruptedException {

        match = MatchApi.getMatch();
        Button playButton = new Button();
        Text text = readyText;
        //text.setX(100);
        //text.setY(100);

        if(match == null){ // Server
            MatchApi.addMatch(
                    LoginController.user.getUsername()
            );
            do {
                match = MatchApi.getMatch();
            } while (Objects.requireNonNull(match).getClient_player().equals(""));
            playButton.setOnAction(actionEvent ->  {
                playClickServer();
            });
            playButton.setText("Play");
            text.setText("You are matched with " + match.getClient_player());
            generalLayout.getChildren().setAll(text,playButton);
        }
        else{ // Client
            match.setClient_player(LoginController.user.getUsername());
            MatchApi.updateMatch(match);
            playButton.setOnAction(actionEvent ->  {
                playClickClient();
            });
            playButton.setText("Play");
            text.setText("You are matched with " + match.getServer_player());
            generalLayout.getChildren().setAll(text);
            generalLayout.getChildren().add(playButton);
            //MatchApi.deleteMatch(match);
        }

    }

    public void playClickServer(){
        match.setStatus("ready");
        MatchApi.updateMatch(match);
        //readyText.setText("Waiting Teammate's response...");
        MultiplayerGame game = new MultiplayerGame(5, 0);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
    }

    public void playClickClient(){
        MultiplayerGame game = new MultiplayerGame(5, 1);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
        MatchApi.deleteMatch(match);
    }

}
