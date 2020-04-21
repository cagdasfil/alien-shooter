package group10.client;

import group10.client.game.Game;
import group10.client.view.MainScreen;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class ClientApplication extends Application {

    // API URL
    public static String hostName = "144.122.71.144:8080/";

    // Global scene for stage
    public static Scene mainScene;

    //  loggedPlayer score
    public static Integer score = 0;

    // Screen width and height
    private int screenWidth = 800;
    private int screenHeight = 900;
    private Game game = new Game();


    @Override
    public void start(Stage primaryStage){


        // First scene of the application
        MainScreen startScreen = new MainScreen("Alien Shooter App","Play");
        startScreen.linkPlayButton(game);
        mainScene = new Scene(startScreen,screenWidth,screenHeight);



        // Set title
        primaryStage.setTitle("ALIEN SHOOTER");

        // Forbid the resizing of the screen
        primaryStage.setResizable(false);
        primaryStage.setScene(mainScene);
        primaryStage.setResizable(true);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

