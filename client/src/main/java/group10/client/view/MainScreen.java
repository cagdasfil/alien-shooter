package group10.client.view;

import group10.client.game.Game;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import static group10.client.ClientApplication.mainScene;

public class MainScreen extends BorderPane {
    protected Text title;
    protected Button playButton;

    public MainScreen(String title, String buttonText) {
        super();
        this.title = new Text(title);
        this.playButton = new Button(buttonText);
        constructPane();
    }

    // Constructs components of the pane ( BorderPane)
    private void  constructPane()
    {
        this.title.setFont(Font.font("Arial",FontWeight.EXTRA_BOLD,50));
        this.setAlignment(this.title,Pos.CENTER);

        this.playButton.setPrefHeight(120);
        this.playButton.setPrefWidth(180);
        this.setAlignment(this.playButton,Pos.CENTER);

        this.setCenter(this.title);
        this.setBottom(this.playButton);
    }

    public void linkPlayButton(Game game){
        this.playButton.setOnAction(e->{
            mainScene.setRoot(game);
            game.setFocusTraversable(true);
            game.requestFocus();
        });
    }

}
