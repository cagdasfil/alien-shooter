package group10.client.game;

import group10.client.api.ScoreApi;
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
    private Player player = null;
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

    private final int W = 800;
    private final int H = 900;

    private final int S = 40;
    private final int playerBulletRadius;
    private final int alienBulletRadius;
    private int alienCount;

    private int playerBulletMovingRate;
    private int playerBulletCratingRate;
    private int alienBulletMovingRate;
    private int alienBulletCreatingRate;
    private int moveDistance = S / 4;

    private GameStatus gameStatus;
    private GameTuner gameTuner;
    private static Integer remainingTime = 100;
    private static int totalKill = 0;
    private static int totalHealth = 3;
    private int gameLevel;
    private boolean isGameOver = false;

    public Game(int gameLevel) {
        this.gameLevel = gameLevel;
        this.gameTuner = new GameTuner();
        this.setStyle("-fx-background-image: url('static/galaxy2.gif')");

        alienCount = gameTuner.getSettings(gameLevel).get("alienCount");
        playerBulletRadius  = gameTuner.getSettings(gameLevel).get("playerBulletRadius");
        alienBulletRadius = gameTuner.getSettings(gameLevel).get("alienBulletRadius");
        playerBulletMovingRate = gameTuner.getSettings(gameLevel).get("playerBulletMovingRate");
        playerBulletCratingRate = gameTuner.getSettings(gameLevel).get("playerBulletCratingRate");
        alienBulletMovingRate = gameTuner.getSettings(gameLevel).get("alienBulletMovingRate");
        alienBulletCreatingRate = gameTuner.getSettings(gameLevel).get("alienBulletCreatingRate");

        initPlayer();
        initAliens();
        initAnimations();
        initGameStatus();
        timer();
        cheat();
    }

    private void initPlayer(){
        this.player = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.BLUE,3);
        this.player.setKills(totalKill);
        this.player.setHealth(totalHealth);
        this.getChildren().add(player);
    }

    private void initAliens(){
        int gap = (W - 2 * S) / alienCount;

        for(int i = 1; i < alienCount +1; i++){
            Alien tankAlien = new TankAlien(W- S/2 - i * gap,H/ 10 * 3, S,S,Color.DARKRED);
            this.aliens.add(tankAlien);
            this.getChildren().add(tankAlien);

            Alien shooterAlien = new ShooterAlien(W- S/2 - i * gap,H/ 10 , S,S,Color.RED);
            this.aliens.add(shooterAlien);
            this.getChildren().add(shooterAlien);

            Alien regularAlien = new Alien(W- S/2 - i * gap,H/ 10 * 2, S,S,Color.GREEN);
            this.aliens.add(regularAlien);
            this.getChildren().add(regularAlien);
        }
    }

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

    public void cheat() {
        this.setOnKeyPressed(keyEvent -> {
            if(keyEvent.isControlDown() && keyEvent.isShiftDown() && keyEvent.getCode() == KeyCode.DIGIT9){
                player.setKills(this.aliens.size() + player.getKills());
                gameEnd();
            }
        });
    }

    private void timer(){
        EventHandler<ActionEvent> timer = actionEvent -> {
            gameStatus.setTime(remainingTime);
            remainingTime -= 1;
            if(remainingTime == 0){
                isGameOver = true;
                gameEnd();
            }
        };
        this.timer.getKeyFrames().add(new KeyFrame(Duration.seconds(1),timer));
        this.timer.setCycleCount(Timeline.INDEFINITE);
        this.timer.play();
    }

    private void alienMovementAnimation(){
        EventHandler<ActionEvent> moveAlien = actionEvent -> {
            Iterator<Alien> it = aliens.iterator();
            while(it.hasNext()){
                Alien alien = it.next();
                alien.setTranslateX(alien.getTranslateX() + moveDistance);
            }
            moveDistance -=1;
            if(moveDistance == - S/4 -1){
                moveDistance = S/4;
            }
        };

        updateAlienAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(100),moveAlien));
        updateAlienAnimation.setCycleCount(Timeline.INDEFINITE);
        updateAlienAnimation.play();
    }

    private void configurePlayerBulletAnimations()
    {
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            int bulletX = (int) (this.player.getX() + this.player.getWidth() / 2);
            int bulletY = (int) (this.player.getY() - playerBulletRadius);

            Bullet bullet = new Bullet( bulletX,bulletY, playerBulletRadius, Color.LIGHTGOLDENRODYELLOW);
            playerBullets.add(bullet);
            this.getChildren().add(bullet);
        };

        createPlayerBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(playerBulletCratingRate),createBullet));
        createPlayerBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createPlayerBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            Iterator<Bullet> it = this.playerBullets.iterator();
            while(it.hasNext()){
                Bullet bullet = it.next();
                double newY = bullet.getCenterY() - 5;
                bullet.setCenterY(newY);
                Iterator<Alien> alienIterator = this.aliens.iterator();
                while(alienIterator.hasNext()){
                    Alien alien = alienIterator.next();
                    if(bullet.getBoundsInParent().intersects(alien.getBoundsInParent())){
                        it.remove();
                        playerBullets.remove(bullet);
                        getChildren().remove(bullet);
                        alien.setHealth(alien.getHealth() - 1);
                        if(alien.getHealth() == 0){
                            player.setKills(player.getKills() +1);
                            gameStatus.setScore(player.getKills());

                            alienIterator.remove();
                            aliens.remove(alien);
                            getChildren().remove(alien);
                        }
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

    private void configureAlienBulletAnimation(){
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            Iterator<Alien> alienIterator = this.aliens.iterator();
            while(alienIterator.hasNext()){
                Alien alien = alienIterator.next();
                if(alien instanceof ShooterAlien){
                    int bulletX = (int) (alien.getTranslateX() + alien.getWidth() / 2);
                    int bulletY = (int) (alien.getTranslateY() + alienBulletRadius);

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
            Iterator<Bullet> it = this.alienBullets.iterator();
            while(it.hasNext()){
                Bullet bullet = it.next();
                double newY = bullet.getCenterY() + 5;
                bullet.setCenterY(newY);

                if(bullet.getBoundsInParent().intersects(player.getBoundsInParent())){
                    it.remove();
                    alienBullets.remove(bullet);
                    getChildren().remove(bullet);
                    player.setHealth(player.getHealth() - 1);

                    gameStatus.setRemainingHealth(player.getHealth());

                    if(player.getHealth() == 0){
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

    private void initAnimations(){
        playerMovementAnimation();
        configurePlayerBulletAnimations();
        configureAlienBulletAnimation();
        alienMovementAnimation();
    }

    private void initGameStatus(){
        gameStatus = new GameStatus(player.getKills(), remainingTime,player.getHealth());
        this.getChildren().add(gameStatus);
    }

    private void gameEnd(){
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
        totalKill = player.getKills();
        totalHealth = player.getHealth();

        if(gameLevel < gameTuner.maxLevel && !isGameOver){
            Game game = new Game(gameLevel + 1);
            game.setFocusTraversable(true);
            ((Pane)this.getParent()).getChildren().setAll(game);
        }

        else {
            int levelBonus = 100 * gameLevel * gameLevel;
            int killBonus = player.getKills() * 10;
            int healthBonus = player.getHealth() * 200;
            int baseScore = levelBonus + killBonus + healthBonus;

            if(gameLevel == 4){
                 baseScore = remainingTime * 50 + baseScore + healthBonus;
            }

            player.setScore(baseScore);

            ScoreApi.saveScore(player.getScore());

            remainingTime = 100;
            totalKill = 0;
            totalHealth = 3;
            goBackGameLobby();

        }
    }

    void goBackGameLobby() {
        try {
            Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
            ((Pane)this.getParent()).getChildren().setAll(gameLobby);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    void debugger(){
        Iterator<Alien> it = aliens.iterator();
        while(it.hasNext()){
            Alien m = it.next();
            System.out.println("X :" + m.getTranslateX() + "Y :" + m.getTranslateY());
        }
    }
}
