package group10.client.multiplayer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import group10.client.api.ScoreApi;
import group10.client.controller.WaitingRoomController;
import group10.client.game.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class MultiplayerGame extends Pane {

    // player object
    private Player player = null;

    //pair object
    private Player pair = null;

    // List of player bullets and boss bullets
    private Boss boss;
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> pairBullets = new ArrayList<>();
    private List<Bullet> bossBullets = new ArrayList<>();

    // Animations of the game
    private Timeline createPlayerBulletAnimation = new Timeline();
    private Timeline updatePlayerBulletAnimation = new Timeline();
    private Timeline createPairBulletAnimation = new Timeline();
    private Timeline updatePairBulletAnimation = new Timeline();
    private Timeline createBossBulletAnimation = new Timeline();
    private Timeline updateBossBulletAnimation = new Timeline();
    private Timeline updateBossAnimation = new Timeline();
    private Timeline timer = new Timeline();
    private Timeline pairMovement = new Timeline();

    // width and height of the game
    private final int W = 800;
    private final int H = 900;

    // width and height for the player objects
    private final int S = 40;

    // player and boss bullet radius
    private final int playerBulletRadius;
    private final int bossBulletRadius;

    // moving and creating rate of the player bullet
    private int playerBulletMovingRate;
    private int playerBulletCratingRate;

    // moving and creating rate of the boss bullet
    private int bossBulletMovingRate;
    private int bossBulletCreatingRate;

    // moving distance for the boss
    private int moveDistance = S / 4;

    // health of the boss
    private int bossHealth;

    // game status object to be able to show remaining time, number of kills and remaining health on the screen
    private GameStatus gameStatus;

    // game settings object. We have different game settings for each different level of the game.
    private GameTuner gameTuner;

    // remaining time for the MultiPlayer level to end
    private Integer remainingTime;

    // game level
    private int gameLevel;

    //Server and client handles p2p communication between two user.
    Server server = null;
    Client client = null;

    private boolean isServerSide = false;

    /**
     * Constructor for the game class
     * @param gameLevel game level
     * @param role role of the user
     */
    public MultiplayerGame(int gameLevel, int role) {
        // set game level
        this.gameLevel = gameLevel;

        //decide whether the current client opens a server for communication or just a connects existing server
        if(role == 0){
            initServerSocket();
            isServerSide = true;
        }
        else if (role == 1){
            initClientSocket();
        }

        // set background image of the scene
        this.setStyle("-fx-background-image: url('static/galaxy2.gif')");

        // load game settings
        this.gameTuner = new GameTuner();

        //load game settings
        playerBulletRadius  = gameTuner.getSettings(gameLevel).get("playerBulletRadius");
        bossBulletRadius = gameTuner.getSettings(gameLevel).get("bossBulletRadius");
        playerBulletMovingRate = gameTuner.getSettings(gameLevel).get("playerBulletMovingRate");
        playerBulletCratingRate = gameTuner.getSettings(gameLevel).get("playerBulletCratingRate");
        bossBulletMovingRate = gameTuner.getSettings(gameLevel).get("bossBulletMovingRate");
        bossBulletCreatingRate = gameTuner.getSettings(gameLevel).get("bossBulletCreatingRate");
        bossHealth = gameTuner.getSettings(gameLevel).get("bossHealth");
        remainingTime = gameTuner.getSettings(gameLevel).get("MultiPlayerGameTime");

        // init player and boss
        initPlayer();
        initBoss();

        // init animations
        initAnimations();

        // init game status indicators
        initGameStatus();

        // init timer
        timer();
    }

    /**
     * This method inits player on the scene
     */
    private void initPlayer(){
        // create new player and pair object
        this.player = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.BLUE,5);
        this.pair = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.GREEN,5);

        // show the new player and pair object on the screen
        this.getChildren().add(player);
        this.getChildren().add(pair);
    }

    /**
     * This method inits Boss
     */
    private void initBoss(){
        //create boss object
        this.boss = new Boss(W/2,H/ 10,4*S,6*S, Color.RED, bossHealth);
        // show boss object on the screen
        this.getChildren().add(boss);
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
     * This method handles pair's movement animation. Spaceship coordinate of the pair object is get by using sockets.
     */
    private void pairMovementAnimation(){
       EventHandler<ActionEvent> o = actionEvent -> {
           sendCoordinateToPair();
       };

       pairMovement.getKeyFrames().add(new KeyFrame(Duration.millis(30),o));
       pairMovement.setCycleCount(Timeline.INDEFINITE);
       pairMovement.play();
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
            if(remainingTime == 0 && isServerSide){
                gameEnd();
            }
        };
        this.timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1),timer));
        this.timer.setCycleCount(Timeline.INDEFINITE);
        this.timer.play();
    }

    /**
     * This method handles movement animation of the boss. Boss moves left and right to be able to escape player bullets.
     */
    private void bossMovementAnimation(){
        EventHandler<ActionEvent> moveBoss = actionEvent -> {

            boss.setTranslateX(boss.getTranslateX() + moveDistance);

            // [2 * S ...  W - 6 * S ]
            if( (boss.getTranslateX() + (6 * S) > W) || (boss.getTranslateX() -  (2 * S) < 0) ) {
                moveDistance *= -1;
            }
        };

        updateBossAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(30),moveBoss));
        updateBossAnimation.setCycleCount(Timeline.INDEFINITE);
        updateBossAnimation.play();
    }

    /**
     * This method handles player bullet animation
     */

    private void configurePairBulletAnimations(){
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            // calculate x and y coordinate of the bullets to be created for the pair
            int bulletX = (int) (this.pair.getTranslateX() + this.pair.getWidth() / 2);
            int bulletY = (int) (this.pair.getTranslateY() - playerBulletRadius);
            // create bullets and show them on scene
            Bullet bullet = new Bullet( bulletX,bulletY, playerBulletRadius, Color.LIGHTGOLDENRODYELLOW);
            pairBullets.add(bullet);
            this.getChildren().add(bullet);
        };

        createPairBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(playerBulletCratingRate),createBullet));
        createPairBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createPairBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            Iterator<Bullet> it = pairBullets.iterator();
            while(it.hasNext()){
                Bullet bullet = it.next();

                double newY = bullet.getCenterY() - 5;
                bullet.setCenterY(newY);


                // check whether pair bullet intersects with the boss or not
                if(bullet.getBoundsInParent().intersects(boss.getBoundsInParent())){
                    // If intersects remove pair bullet from list and scene
                    it.remove();
                    pairBullets.remove(bullet);
                    getChildren().remove(bullet);

                    if(isServerSide){
                        // decrement the health of the boss who shot
                        boss.setHealth(boss.getHealth() - 1);

                        // increment number of hits to boss variable by one
                        pair.setHitBoss(pair.getHitBoss()+1);

                        //System.out.println("Pair boss hit : " + pair.getHitBoss());

                        // If boss has no remaining health
                        if(boss.getHealth() == 0){
                            sendCoordinateToPair();
                            // increment number of kills of the player by one and update game status indicator located on screen
                            pair.setKills(pair.getKills() +1);
                            getChildren().remove(boss);
                            gameEnd();
                        }
                    }


                }
            }
        };

        updatePairBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(playerBulletMovingRate),updateBullets));
        updatePairBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        updatePairBulletAnimation.play();
    }
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


                // check whether player bullet intersects with the boss or not
                if(bullet.getBoundsInParent().intersects(boss.getBoundsInParent())){
                    // If intersects remove player bullet from list and scene
                    it.remove();
                    playerBullets.remove(bullet);
                    getChildren().remove(bullet);

                    if(isServerSide){
                        // decrement the health of the boss who shot
                        boss.setHealth(boss.getHealth() - 1);
                        // increment number of hits to boss variable by one
                        player.setHitBoss(player.getHitBoss()+1);
                        gameStatus.setHitBoss(player.getHitBoss());

                        //System.out.println("Player boss hit : " + player.getHitBoss());
                        // If boss has no remaining health
                        if(boss.getHealth() == 0){
                            // increment number of kills of the player by one and update game status indicator located on screen
                            player.setKills(player.getKills() +1);
                            //gameStatus.setKill(player.getKills());

                            // remove the boss from the scene
                            getChildren().remove(boss);
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
     * This method handles boss bullet animation
     */
    private void configureBossBulletAnimation(){
        EventHandler<ActionEvent> createBullet = actionEvent -> {

            // calculate x and y coordinate of the bullet for the boss
            int bulletX = (int) (boss.getTranslateX() + boss.getWidth() / 2);
            int bulletY = (int) (boss.getTranslateY() + bossBulletRadius);

            // create and show the bullet on screen
            Bullet bullet = new Bullet( bulletX,bulletY, bossBulletRadius, Color.ORANGE);
            bossBullets.add(bullet);
            this.getChildren().add(bullet);
        };

        createBossBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(bossBulletCreatingRate),createBullet));
        createBossBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createBossBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            // iterator for boss bullets
            Iterator<Bullet> it = this.bossBullets.iterator();

            // iterate over boss bullets
            while(it.hasNext()){
                Bullet bullet = it.next();

                // calculate and set new y coordinate for the bullet
                double newY = bullet.getCenterY() + 5;
                bullet.setCenterY(newY);

                // check whether the bullet intersects with the player or not
                if(bullet.getBoundsInParent().intersects(player.getBoundsInParent())){
                    // If intersects remove the bullet from the list and scene
                    it.remove();
                    bossBullets.remove(bullet);
                    getChildren().remove(bullet);

                    if(isServerSide){
                        // decrement player health by one
                        player.setHealth(player.getHealth() - 1);

                        // update game status indicator located on screen
                        gameStatus.setRemainingHealth(player.getHealth());

                        // check whether player has remaining health or not
                        if(player.getHealth() == 0){

                            // If not remove player from the screen and gameover!
                            getChildren().remove(player);
                            gameEnd();
                        }
                    }

                }
                // check whether the bullet intersects with the pair or not
                else if(bullet.getBoundsInParent().intersects(pair.getBoundsInParent())){
                    // If intersects remove the bullet from the list and scene
                    it.remove();
                    bossBullets.remove(bullet);
                    getChildren().remove(bullet);

                    if(isServerSide){
                        // decrement pair health by one
                        pair.setHealth(pair.getHealth() - 1);

                        // check whether pair has remaining health or not
                        if(pair.getHealth() == 0){

                            // If not remove pair from the screen and gameover!
                            getChildren().remove(pair);
                            gameEnd();
                        }
                    }
                }
            }
        };

        updateBossBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(bossBulletMovingRate),updateBullets));
        updateBossBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        updateBossBulletAnimation.play();

    }

    /**
     * This method initialize all animations in the game
     */
    private void initAnimations(){
        playerMovementAnimation();
        pairMovementAnimation();
        configurePlayerBulletAnimations();
        configurePairBulletAnimations();
        configureBossBulletAnimation();
        bossMovementAnimation();
    }

    /**
     * This method initialize game status indicator that shows remaining time, number of kills and remaining health of the player on screen
     */
    private void initGameStatus(){
        // create new game status object and add it on screen
        gameStatus = new GameStatus(player.getHitBoss(), remainingTime,player.getHealth(),true);
        this.getChildren().add(gameStatus);
    }

    /**
     * This method ends game or pass game to the next level. If game ends, the score of player is calculated and sends to the server
     */
    private void gameEnd(){
        // stop animations and clean scene
        createBossBulletAnimation.stop();
        updateBossBulletAnimation.stop();
        createPlayerBulletAnimation.stop();
        updatePlayerBulletAnimation.stop();
        createPairBulletAnimation.stop();
        updatePairBulletAnimation.stop();
        updateBossAnimation.stop();
        pairMovement.stop();
        timer.stop();

        createBossBulletAnimation.getKeyFrames().clear();
        updateBossBulletAnimation.getKeyFrames().clear();
        createPlayerBulletAnimation.getKeyFrames().clear();
        updatePlayerBulletAnimation.getKeyFrames().clear();
        createPairBulletAnimation.getKeyFrames().clear();
        updatePairBulletAnimation.getKeyFrames().clear();
        updateBossAnimation.getKeyFrames().clear();
        pairMovement.getKeyFrames().clear();
        timer.getKeyFrames().clear();

        this.getChildren().removeAll();
        System.out.println("Player number of boss hits : " + player.getHitBoss());
        System.out.println("Pair number of boss hits : " + pair.getHitBoss());
        System.out.println("Boss health :" + boss.getHealth());
        if(isServerSide){
            try{
                GameData gameData = new GameData(true);
                this.server.sendToAllTCP(gameData);
                Thread.sleep(100);
                this.server.close();
            }
            catch (Exception e){
                System.out.println("connection cannot closed");
            }
        }

        /* calculate scores*/

        // most hit bonus 1000
        int mostHitBonus = 1000;
        // calculate base scores of the player and pair
        int playerScore = player.getHitBoss() * 100;
        int playerBonus = 0;
        int pairScore = (bossHealth - player.getHitBoss()) * 100;
        int pairBonus = 0;

        // set mostHitBonus(1000) to the user that have most hits.
        if(player.getHitBoss() > (bossHealth - player.getHitBoss())){
            playerBonus = mostHitBonus;
        }
        else{
            pairBonus = mostHitBonus;
        }
        //post the final score.
        ScoreApi.saveScore(playerScore+playerBonus);
        // show each user his/her score and pair's score
        showScores(playerScore, playerBonus, pairScore, pairBonus);
    }

    /**
     * This method inits Server Side for the p2p communication between users in the multiplayer level.
     */
    void initServerSocket(){
        // initialize the Server for the p2p communication
        this.server = new Server();
        // register the classes that will be transmitted
        Kryo kryo = this.server.getKryo();
        kryo.register(GameData.class);
        // add action listener
        this.server.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof  GameData){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GameData gameData = (GameData ) object;
                            // get pair's coordinate
                            pair.setTranslateX(gameData.getPlayerX());
                            pair.setTranslateY(gameData.getPlayerY());
                        }
                    });

                }
            }
        });
        // bind tcp port for the server
        try {
            this.server.bind(9876);
        }
        catch (IOException e){
            System.out.println(e);
        }
        server.start();
    }

    /**
     * This method inits Client Side for the p2p communication between users in the multiplayer level.
     */

    void initClientSocket(){
        // Initialize Client Side for the p2p communication
        this.client = new Client();
        // register the classes that will be transmitted
        Kryo kryo = this.client.getKryo();
        kryo.register(GameData.class);

        // add action listener
        this.client.addListener(new Listener(){
            @Override
            public void received(Connection connection, Object object) {
                if(object instanceof  GameData){
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            GameData gameData = (GameData ) object;
                            if(gameData.getGameFinished()){
                                gameEnd();
                            }
                            else{
                                // get pair's and boss's coordinate and set them on the screen
                                pair.setTranslateX(gameData.getPlayerX());
                                pair.setTranslateY(gameData.getPlayerY());
                                boss.setTranslateX(gameData.getBossX());

                                player.setHitBoss(gameData.getBossHit());
                                player.setHealth(gameData.getPlayerHealth());
                                gameStatus.setHitBoss(gameData.getBossHit());
                                gameStatus.setRemainingHealth(gameData.getPlayerHealth());
                            }

                        }
                    });
                }
            }
        });

        // start the client
        this.client.start();

        // connect client to the server
        try {
            this.client.connect(5000,
                    WaitingRoomController.match.getServerIP(),
                    Integer.parseInt(WaitingRoomController.match.getServerPort()));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    void sendCoordinateToPair(){
        if(isServerSide){
            try{
                // If you are server side in the p2p communication, send own and boss's coordinate. Also send pairBosshits and pairHealth
                Double playerX = player.getTranslateX() + player.getX();
                Double playerY = player.getTranslateY() + player.getY();
                Double bossX = boss.getTranslateX() + boss.getX();


                Integer pairBossHits = pair.getHitBoss();
                Integer pairHealth = pair.getHealth();

                GameData gameData = new GameData(playerX,playerY,bossX,pairBossHits,pairHealth);
                this.server.sendToAllTCP(gameData);
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
        else {
            try{
                // If you are client side in the p2p communication, send own coordinate.
                Double playerX = player.getTranslateX()+ player.getX();
                Double playerY = player.getTranslateY() + player.getY();

                GameData gameData = new GameData(playerX,playerY);
                this.client.sendTCP(gameData);
            }
            catch (Exception e){
                if(e.getMessage().equals("Socket closed")){
                    gameEnd();
                }
            }
        }
    }

    /**
     * This method shows own and pair's score to the user.
     */

    void showScores(int playerScore, int playerBonus, int pairScore, int pairBonus){
        Alert scores = new Alert(Alert.AlertType.INFORMATION);
        scores.setTitle("Scores");
        scores.setHeaderText("Game Over !");
        scores.setContentText("Total Score : " + (playerScore+playerBonus+pairScore+pairBonus)
                                +"\nYour Score : " + playerScore + " + " + playerBonus
                                +"\nTeammate's Score : " + pairScore + " + " + pairBonus);
        scores.show();
        goBackGameLobby();
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
