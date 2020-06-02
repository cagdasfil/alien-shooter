package group10.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import group10.client.api.MatchApi;
import group10.client.model.server.Match;
import group10.client.multiplayer.MultiplayerGame;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is controller for the WaitingRoom.fxml file
 */
@Component
public class WaitingRoomController implements Initializable {

    @FXML public Pane generalLayout;
    @FXML public Text text;
    @FXML public Text informText;
    @FXML public Button playButton;
    @FXML public ProgressIndicator progress;

    public static Match match;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Hide the play button before match
        playButton.setVisible(false);
        // Get match from database
        try {
            match = MatchApi.getMatch();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        // If there is no match in database, take server role.
        if(match == null){ // SERVER
            // Create a match and save to database
            MatchApi.addMatch();
            // Thread to wait until a client comes
            Thread t = new Thread(() -> {
                do {
                    try {
                        match = MatchApi.getMatch();
                    } catch (JsonProcessingException e) {
                        e.printStackTrace();
                    }
                } while (Objects.requireNonNull(match).getClientUsername().equals(""));
                // Direct playButton to playClickServer function
                playButton.setOnAction(actionEvent ->  {
                    playClickServer();
                });
                // Set visibility of the components
                playButton.setVisible(true);
                informText.setVisible(false);
                progress.setVisible(false);
                // Inform player about matched player
                text.setText("You are matched with " + match.getClientUsername());
            });
            t.start();
        }
        // if there is a match in server, take client role.
        else{ // CLIENT
            // Send client information to server
            match.setClientUsername(LoginController.user.getUsername());
            MatchApi.updateMatch(match);
            // Inform player about matched player
            text.setText("You are matched with " + match.getServerUsername());
            // Direct playButton to playClickClient function
            playButton.setOnAction(actionEvent ->  {
                playClickClient();
            });
            // Set visibility of the components
            playButton.setVisible(true);
            informText.setVisible(false);
            progress.setVisible(false);
        }
    }

    /**
     * Controller function of the play button click for server player
     */
    public void playClickServer(){
        // Wait until the client player is ready
        do {
            try {
                match = MatchApi.getMatch();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } while (Objects.requireNonNull(match).getClientStatus().equals("wait"));
        // Create a multiplayer game and start
        MultiplayerGame game = new MultiplayerGame(5, 0);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
        // Set status to inform client player
        match.setServerStatus("ready");
        MatchApi.updateMatch(match);
    }

    /**
     * Controller function of the play button click for client player
     */
    public void playClickClient(){
        // Set status to inform server player
        match.setClientStatus("ready");
        MatchApi.updateMatch(match);
        // Wait until the server player is ready
        do {
            try {
                match = MatchApi.getMatch();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } while (Objects.requireNonNull(match).getServerStatus().equals("wait"));
        // Create a multiplayer game and start
        MultiplayerGame game = new MultiplayerGame(5, 1);
        game.setFocusTraversable(true);
        generalLayout.getChildren().setAll(game);
        // Delete the match entry
        MatchApi.deleteMatch(match);
    }

}
