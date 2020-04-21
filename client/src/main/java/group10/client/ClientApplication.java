package group10.client;

import group10.client.game.Game;
import group10.client.view.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class ClientApplication extends Application {
    public static String hostName = "localhost:8080/";

    // Global scene for stage
    public static Scene mainScene;


    // Screen width and height
    private int screenWidth = 800;
    private int screenHeight = 900;
    private Game game = new Game();

    @Override
    public void start(Stage mainStage){

        MainScreen mainScreen = new MainScreen("Galaxy Attack: Alien Shooter”","Play");
        mainScreen.linkPlayButton(game);
        mainScene = new Scene(mainScreen,screenWidth,screenHeight);

        mainStage.setTitle("Galaxy Attack: Alien Shooter");

        mainStage.setScene(mainScene);
        mainStage.setResizable(true);
        mainStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

