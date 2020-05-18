package group10.client.game;

import group10.client.api.ScoreApi;
import group10.client.controller.LoginController;
import group10.client.multiplayer.MultiplayerGame;
import group10.client.multiplayer.SocketClient;
import group10.client.multiplayer.SocketServer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Game extends Pane {
    // player object
    private Player player = null;

    // List of aliens, player bullets and alien bullets
    private List<Alien> aliens = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> alienBullets = new ArrayList<>();

    // Animations of the game
    private Timeline createPlayerBulletAnimation = new Timeline();
    private Timeline updatePlayerBulletAnimation = new Timeline();
    private Timeline createAlienBulletAnimation = new Timeline();
    private Timeline updateAlienBulletAnimation = new Timeline();
    private Timeline updateAlienAnimation = new Timeline();
    private Timeline timer = new Timeline();

    // width and height of the game
    private final int W = 800;
    private final int H = 900;

    // width and height for the player and alien objects
    private final int S = 40;

    // player and alien bullet radius
    private final int playerBulletRadius;
    private final int alienBulletRadius;

    // number of aliens in a row
    private int alienCount;

    // moving and creating rate of the player bullet
    private int playerBulletMovingRate;
    private int playerBulletCratingRate;

    // moving and creating rate of the alien bullet
    private int alienBulletMovingRate;
    private int alienBulletCreatingRate;

    // moving distance for the aliens
    private int moveDistance = S / 4;

    // health of the tank aliens
    private int tankAlienHealth;

    // game status object to be able to show remaining time, number of kills and remaining health on the screen
    private GameStatus gameStatus;

    // game settings object. We have different game settings for each different level of the game.
    private GameTuner gameTuner;

    // remaining time for the game to end
    private static Integer remainingTime = 100;

    // variables to hold number of kills and remaining health for the player between different game levels.
    private static int totalKill = 0;
    private static int totalHealth = 3;

    // game level
    private int gameLevel;

    // variable to be set when the player has no remaining health or time finishes.
    private boolean isGameOver = false;

    /**
     * Constructor for the game class
     * @param gameLevel game level
     */
    public Game(int gameLevel) {
        // set game level
        this.gameLevel = gameLevel;

        // set background image of the scene
        this.setStyle("-fx-background-image: url('static/galaxy2.gif')");

        // load game settings
        this.gameTuner = new GameTuner();
        alienCount = gameTuner.getSettings(gameLevel).get("alienCount");
        playerBulletRadius  = gameTuner.getSettings(gameLevel).get("playerBulletRadius");
        alienBulletRadius = gameTuner.getSettings(gameLevel).get("alienBulletRadius");
        playerBulletMovingRate = gameTuner.getSettings(gameLevel).get("playerBulletMovingRate");
        playerBulletCratingRate = gameTuner.getSettings(gameLevel).get("playerBulletCratingRate");
        alienBulletMovingRate = gameTuner.getSettings(gameLevel).get("alienBulletMovingRate");
        alienBulletCreatingRate = gameTuner.getSettings(gameLevel).get("alienBulletCreatingRate");
        tankAlienHealth = gameTuner.getSettings(gameLevel).get("tankAlienHealth");

        // init player and aliens
        initPlayer();
        initAliens();

        // init animations
        initAnimations();

        // init game status indicators
        initGameStatus();

        // init timer
        timer();

        // activate cheat CTRL + SHIFT + 9
        cheat();
    }

    /**
     * This method inits player on the scene
     */
    private void initPlayer(){
        // create new player object
        this.player = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.BLUE,3);
        this.player.setKills(totalKill);
        this.player.setHealth(totalHealth);

        // show the new player object on the screen
        this.getChildren().add(player);
    }

    /**
     * This method inits simple aliens, shooter aliens and tank aliens on the scene
     */
    private void initAliens(){
        // distance between each alien object
        int gap = (W - 2 * S) / alienCount;

        for(int i = 1; i < alienCount +1; i++){
            // create tank aliens with given count and show them on screen
            Alien tankAlien = new TankAlien(W- S/2 - i * gap,H/ 10 * 3, S,S,Color.DARKRED,tankAlienHealth);
            this.aliens.add(tankAlien);
            this.getChildren().add(tankAlien);

            // create shooter aliens with given count and show them on screen
            Alien shooterAlien = new ShooterAlien(W- S/2 - i * gap,H/ 10 , S,S,Color.RED);
            this.aliens.add(shooterAlien);
            this.getChildren().add(shooterAlien);

            // create simple aliens with given count and show them on screen
            Alien regularAlien = new Alien(W- S/2 - i * gap,H/ 10 * 2, S,S,Color.GREEN);
            this.aliens.add(regularAlien);
            this.getChildren().add(regularAlien);
        }
    }

    /**
     * This method handles player movement animation, player can be moved with mouse
     */
    private void playerMovementAnimation(){
        TranslateTransition transition = new TranslateTransition(Duration.millis(1),player);
        transition.setOnFinished(t-> {
            player.setX(player.getTranslateX()+ player.getX());
            player.setY(player.getTranslateY()+ player.getY());
            player.setTranslateX(0);
            player.setTranslateY(0);

        });

        this.setOnMouseMoved(e->{
            transition.stop();
            transition.setToX(e.getX() - S / 2 - player.getX());
            transition.setToY(e.getY()- S / 2 - player.getY());
            transition.playFromStart();
        });
    }

    /**
     *  This method activates cheat that automatically passes the game to the next level (CTRL + SHIFT + 9 )
     */
    public void cheat() {
        this.setOnKeyPressed(keyEvent -> {
            // If user pressed (CTRL + SHIFT + 9) combination , kill all the aliens that are alive and pass game to next level
            if(keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT9){
                player.setKills(this.aliens.size() + player.getKills());
                gameEnd();
            }
        });
    }

    /**
     * This method initialize the timer and counts remaining time to end game
     */
    private void timer(){
        EventHandler<ActionEvent> timer = actionEvent -> {
            // start timer
            gameStatus.setTime(remainingTime);
            // decrement remaining time by one for each second
            remainingTime -= 1;
            // if remaining time equals to zero, gameover!
            if(remainingTime == 0){
                isGameOver = true;
                gameEnd();
            }
        };
        this.timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1),timer));
        this.timer.setCycleCount(Timeline.INDEFINITE);
        this.timer.play();
    }

    /**
     * This method handles movement animation of the aliens. Aliens move left and right to be able to escape player bullets.
     */
    private void alienMovementAnimation(){
        EventHandler<ActionEvent> moveAlien = actionEvent -> {
            // iterator for aliens
            Iterator<Alien> it = aliens.iterator();

            // iterate over aliens and move them with move distance
            while(it.hasNext()){
                Alien alien = it.next();
                alien.setTranslateX(alien.getTranslateX() + moveDistance);
            }
            // decrement move distance
            moveDistance -=1;

            // move distance = [-S/4, S/4]
            if(moveDistance == - S/4 -1){
                moveDistance = S/4;
            }
        };

        updateAlienAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(100),moveAlien));
        updateAlienAnimation.setCycleCount(Timeline.INDEFINITE);
        updateAlienAnimation.play();
    }

    /**
     * This method handles player bullet animation
     */
    private void configurePlayerBulletAnimations()
    {
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            // calculate x and y coordinate of the bullets to be created related to the player
            int bulletX = (int) (this.player.getX() + this.player.getWidth() / 2);
            int bulletY = (int) (this.player.getY() - playerBulletRadius);

            // create bullets and show them on scene
            Bullet bullet = new Bullet( bulletX,bulletY, playerBulletRadius, Color.LIGHTGOLDENRODYELLOW);
            playerBullets.add(bullet);
            this.getChildren().add(bullet);
        };

        createPlayerBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(playerBulletCratingRate),createBullet));
        createPlayerBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createPlayerBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            // iterator for player bullets
            Iterator<Bullet> it = this.playerBullets.iterator();

            // iterate over player bullets
            while(it.hasNext()){
                Bullet bullet = it.next();

                // calculate new y coordinate for the player bullet
                double newY = bullet.getCenterY() - 5;
                bullet.setCenterY(newY);

                // iterator for aliens
                Iterator<Alien> alienIterator = this.aliens.iterator();

                // iterate over aliens
                while(alienIterator.hasNext()){
                    Alien alien = alienIterator.next();

                    // check whether player bullet intersects with the alien or not
                    if(bullet.getBoundsInParent().intersects(alien.getBoundsInParent())){
                        // If intersects remove player bullet from list and scene
                        it.remove();
                        playerBullets.remove(bullet);
                        getChildren().remove(bullet);

                        // decrement the health of the alien who shot
                        alien.setHealth(alien.getHealth() - 1);

                        // If alien has no remaining health
                        if(alien.getHealth() == 0){
                            // increment number of kills of the player by one and update game status indicator located on screen
                            player.setKills(player.getKills() +1);
                            gameStatus.setKill(player.getKills());

                            // remove the alien from the list and scene
                            alienIterator.remove();
                            aliens.remove(alien);
                            getChildren().remove(alien);
                        }

                        // ıf there is no remaining alien finish the game
                        if(aliens.size() == 0){
                            gameEnd();
                        }

                    }
                }
            }
        };

        updatePlayerBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(playerBulletMovingRate),updateBullets));
        updatePlayerBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        updatePlayerBulletAnimation.play();
    }

    /**
     * This method handles alien bullet animation
     */
    private void configureAlienBulletAnimation(){
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            // iterator for aliens
            Iterator<Alien> alienIterator = this.aliens.iterator();

            // iterate over aliens
            while(alienIterator.hasNext()){
                Alien alien = alienIterator.next();

                // If the type of the alien is Shooter Alien
                if(alien instanceof ShooterAlien){

                    // calculate x and y coordinate of the bullet for shooter alien
                    int bulletX = (int) (alien.getTranslateX() + alien.getWidth() / 2);
                    int bulletY = (int) (alien.getTranslateY() + alienBulletRadius);

                    // create and show the bullet on screen
                    Bullet bullet = new Bullet( bulletX,bulletY, alienBulletRadius, Color.ORANGE);
                    alienBullets.add(bullet);
                    this.getChildren().add(bullet);
                }
            }
        };

        createAlienBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(alienBulletCreatingRate),createBullet));
        createAlienBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createAlienBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            // iterator for alien bullets
            Iterator<Bullet> it = this.alienBullets.iterator();

            // iterate over alien bullets
            while(it.hasNext()){
                Bullet bullet = it.next();

                // calculate and set new y coordinate for the bullet
                double newY = bullet.getCenterY() + 5;
                bullet.setCenterY(newY);

                // check whether the bullet intersects with the player or not
                if(bullet.getBoundsInParent().intersects(player.getBoundsInParent())){
                    // If intersects remove the bullet from the list and scene
                    it.remove();
                    alienBullets.remove(bullet);
                    getChildren().remove(bullet);

                    // decrement player health by one
                    player.setHealth(player.getHealth() - 1);

                    // update game status indicator located on screen
                    gameStatus.setRemainingHealth(player.getHealth());

                    // check whether player has remaining health or not
                    if(player.getHealth() == 0){

                        // If not remove player from the screen and gameover!
                        getChildren().remove(player);
                        isGameOver = true;
                        gameEnd();
                    }
                }
            }
        };

        updateAlienBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(alienBulletMovingRate),updateBullets));
        updateAlienBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        updateAlienBulletAnimation.play();

    }

    /**
     * This method initialize all animations in the game
     */
    private void initAnimations(){
        playerMovementAnimation();
        configurePlayerBulletAnimations();
        configureAlienBulletAnimation();
        alienMovementAnimation();
    }

    /**
     * This method initialize game status indicator that shows remaining time, number of kills and remaining health of the player on screen
     */
    private void initGameStatus(){
        // create new game status object and add it on screen
        gameStatus = new GameStatus(player.getKills(), remainingTime,player.getHealth());
        this.getChildren().add(gameStatus);
    }

    /**
     * This method ends game or pass game to the next level. If game ends, the score of player is calculated and sends to the server
     */
    private void gameEnd(){
        // stop animations and clean scene
        createAlienBulletAnimation.stop();
        updateAlienBulletAnimation.stop();
        createPlayerBulletAnimation.stop();
        updatePlayerBulletAnimation.stop();
        updateAlienAnimation.stop();
        timer.stop();
        createAlienBulletAnimation.getKeyFrames().clear();
        updateAlienBulletAnimation.getKeyFrames().clear();
        createPlayerBulletAnimation.getKeyFrames().clear();
        updatePlayerBulletAnimation.getKeyFrames().clear();
        updateAlienAnimation.getKeyFrames().clear();
        timer.getKeyFrames().clear();
        this.getChildren().removeAll();

        // assign static variables that holds remaining health and total number of kills of the player between different game levels.
        totalKill = player.getKills();
        totalHealth = player.getHealth();

        // check whether game passes next level or not
        if(gameLevel < gameTuner.maxLevel && !isGameOver){
            // increment game level by one and create new game object. Then, show it on screen
            Game game = new Game(gameLevel + 1);
            game.setFocusTraversable(true);
            ((Pane)this.getParent()).getChildren().setAll(game);
        }
        else if (isGameOver){
            // If player dies , calculate the total score of the player
            int levelBonus = 100 * gameLevel * gameLevel;
            int killBonus = player.getKills() * 10;
            int healthBonus = player.getHealth() * 200;
            int baseScore = levelBonus + killBonus + healthBonus;

            // If player reaches the highest level , add time bonus to the base score
            if(gameLevel == 4){
                baseScore = remainingTime * 50 + baseScore;
            }

            // set score of the player
            player.setScore(baseScore);

            // post the total score of the player to the database
            ScoreApi.saveScore(player.getScore());

            // set static variables before go back to lobby
            remainingTime = 100;
            totalKill = 0;
            totalHealth = 3;

            // go back game lobby
            goBackGameLobby();
        }

        else if (gameLevel == gameTuner.maxLevel) {
            // If player completed single player levels, go MultiPlayer level

            if(LoginController.user.getUsername().equals("user")){

                try {
                    SocketServer socketServer = new SocketServer();
                    socketServer.readMessage();
                    socketServer.sendMessage("hello client");
                    MultiplayerGame game = new MultiplayerGame(1, socketServer);
                    game.setFocusTraversable(true);
                    ((Pane)this.getParent()).getChildren().setAll(game);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }

            else if(LoginController.user.getUsername().equals("admin")){
                try {
                    SocketClient socketClient = new SocketClient();
                    socketClient.sendMessage("hello server");
                    socketClient.readMessage();
                    MultiplayerGame game = new MultiplayerGame(1, socketClient);
                    game.setFocusTraversable(true);
                    ((Pane)this.getParent()).getChildren().setAll(game);
                }
                catch (Exception e){
                    System.out.println(e);
                }

            }

        }
    }

    /**
     * This method shows game lobby on the screen when the game ends
     */
    void goBackGameLobby() {
        try {
            // show game lobby on the screen
            Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
            ((Pane)this.getParent()).getChildren().setAll(gameLobby);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }
}
