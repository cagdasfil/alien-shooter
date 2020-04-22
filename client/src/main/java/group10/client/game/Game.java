package group10.client.game;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Game extends Pane {
    private Player player = null;
    private List<Alien> aliens = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> monsterBullets = new ArrayList<>();

    // Animations of the game
    private Timeline createPlayerBulletAnimation = new Timeline();
    private Timeline updatePlayerBulletAnimation = new Timeline();
    private Timeline createMonsterBulletAnimation = new Timeline();
    private Timeline updateMonsterBulletAnimation = new Timeline();
    private Timeline updateMonsterAnimation = new Timeline();
    private Timeline timeLine = new Timeline();
    private static Integer startTime = 0;


    private final int W = 800;
    private final int H = 900;

    private final int S = 40;
    private final int playerBulletRadius = 15;
    private final int monsterBulletRadius = 10;
    private int monsterCount = 8 ;

    private int playerBulletMovingRate = 10;
    private int playerBulletCratingRate = 150; // ms
    private int monsterBulletMovingRate = 50;
    private int monsterBulletCreatingRate = 4; // second
    private int moveDistance = S / 4;

    private GameStatus gameStatus;

    public Game() {
        this.setStyle("-fx-background-image: url('static/galaxy2.gif')");
        initPlayer();
        initMonsters();
        playerMovementAnimation();
        configurePlayerBulletAnimations();
        configureMonsterBulletAnimation();
        monsterMovementAnimation();
        gameStatus = new GameStatus(player.getKills(),5,player.getHealth());
        this.getChildren().add(gameStatus);
        timer();

    }

    private void initPlayer(){
        this.player = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.BLUE,3);
        this.getChildren().add(player);
    }

    private void initMonsters(){
        int gap = (W - 2 * S) / monsterCount;

        for(int i = 1 ; i < monsterCount +1; i++){
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

    private void timer(){
        EventHandler<ActionEvent> timer = actionEvent -> {
            gameStatus.setTime(startTime);
            startTime += 1;
        };
        timeLine.getKeyFrames().add(new KeyFrame(Duration.seconds(1),timer));
        timeLine.setCycleCount(Timeline.INDEFINITE);
        timeLine.play();
    }

    private void monsterMovementAnimation(){
        EventHandler<ActionEvent> moveMonster = actionEvent -> {
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

        updateMonsterAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(100),moveMonster));
        updateMonsterAnimation.setCycleCount(Timeline.INDEFINITE);
        updateMonsterAnimation.play();
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
                Iterator<Alien> monsterIterator = this.aliens.iterator();
                while(monsterIterator.hasNext()){
                    Alien alien = monsterIterator.next();
                    if(bullet.getBoundsInParent().intersects(alien.getBoundsInParent())){
                        it.remove();
                        playerBullets.remove(bullet);
                        getChildren().remove(bullet);
                        alien.setHealth(alien.getHealth() - 1);
                        if(alien.getHealth() == 0){
                            player.setKills(player.getKills() +1);
                            gameStatus.setScore(player.getKills());

                            monsterIterator.remove();
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

    private void configureMonsterBulletAnimation(){ ;
        EventHandler<ActionEvent> createBullet = actionEvent -> {
            Iterator<Alien> monsterIterator = this.aliens.iterator();
            while(monsterIterator.hasNext()){
                Alien alien = monsterIterator.next();
                if(alien instanceof ShooterAlien){
                    int bulletX = (int) (alien.getTranslateX() + alien.getWidth() / 2);
                    int bulletY = (int) (alien.getTranslateY() + monsterBulletRadius);

                    Bullet bullet = new Bullet( bulletX,bulletY, monsterBulletRadius, Color.ORANGE);
                    monsterBullets.add(bullet);
                    this.getChildren().add(bullet);
                }
            }
        };

        createMonsterBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.seconds(monsterBulletCreatingRate),createBullet));
        createMonsterBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        createMonsterBulletAnimation.play();

        EventHandler<ActionEvent> updateBullets = actionEvent -> {
            Iterator<Bullet> it = this.monsterBullets.iterator();
            while(it.hasNext()){
                Bullet bullet = it.next();
                double newY = bullet.getCenterY() + 5;
                bullet.setCenterY(newY);

                if(bullet.getBoundsInParent().intersects(player.getBoundsInParent())){
                    it.remove();
                    monsterBullets.remove(bullet);
                    getChildren().remove(bullet);
                    player.setHealth(player.getHealth() - 1);

                    gameStatus.setRemainingHealth(player.getHealth());

                    if(player.getHealth() == 0){
                        getChildren().remove(player);
                        gameEnd();
                    }
                }
            }
        };

        updateMonsterBulletAnimation.getKeyFrames().add(new KeyFrame(Duration.millis(monsterBulletMovingRate),updateBullets));
        updateMonsterBulletAnimation.setCycleCount(Timeline.INDEFINITE);
        updateMonsterBulletAnimation.play();

    }

    private void gameEnd(){
        createMonsterBulletAnimation.stop();
        updateMonsterBulletAnimation.stop();
        createPlayerBulletAnimation.stop();
        updatePlayerBulletAnimation.stop();
        updateMonsterAnimation.stop();
        timeLine.stop();
        createMonsterBulletAnimation.getKeyFrames().clear();
        updateMonsterBulletAnimation.getKeyFrames().clear();
        createPlayerBulletAnimation.getKeyFrames().clear();
        updatePlayerBulletAnimation.getKeyFrames().clear();
        updateMonsterAnimation.getKeyFrames().clear();
        timeLine.getKeyFrames().clear();

        int killAllBonus = 0;

        if(aliens.size() == 0){
            killAllBonus += 500;
        }

        int score = (100/startTime) * player.getKills() + killAllBonus;
        player.setScore(score);
        System.out.println("Final score :" + player.getScore());
    }

    void debugger(){
        Iterator<Alien> it = aliens.iterator();
        while(it.hasNext()){
            Alien m = it.next();
            System.out.println("X :" + m.getTranslateX() + "Y :" + m.getTranslateY());
        }
    }
}
