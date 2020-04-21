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
    private List<Monster> monsters = new ArrayList<>();
    private List<Bullet> playerBullets = new ArrayList<>();
    private List<Bullet> monsterBullets = new ArrayList<>();

    // Animations of the game
    private Timeline createPlayerBulletAnimation = new Timeline();;
    private Timeline updatePlayerBulletAnimation = new Timeline();;
    private Timeline createMonsterBulletAnimation = new Timeline();;
    private Timeline updateMonsterBulletAnimation = new Timeline();;


    private final int W = 800;
    private final int H = 600;

    private final int S = 40;
    private final int playerBulletRadius = 15;
    private final int monsterBulletRadius = 10;
    private int monsterCount = 8 ;

    private int playerBulletMovingRate = 10;
    private int playerBulletCratingRate = 150; // ms
    private int monsterBulletMovingRate = 50;
    private int monsterBulletCreatingRate = 4; // second

    public Game() {
        this.setStyle("-fx-background-image: url('galaxy.jpg')");
        initPlayer();
        initMonsters();
        playerMovementAnimation();
        debugger();
        configurePlayerBulletAnimations();
        configureMonsterBulletAnimation();

    }

    private void initPlayer(){
        this.player = new Player(W/2 - S/2,H/ 10 * 9,2*S,3*S, Color.BLUE,3);
        this.getChildren().add(player);
    }

    private void initMonsters(){
        for(int i = 1 ; i < monsterCount +1; i++){
            Monster tankMonster = new TankMonster(W- S/2 - i *90,H/ 10 * 3, S,S,Color.DARKRED);
            this.monsters.add(tankMonster);
            this.getChildren().add(tankMonster);

            Monster shooterMonster = new ShooterMonster(W- S/2 - i *90,H/ 10 , S,S,Color.RED);
            this.monsters.add(shooterMonster);
            this.getChildren().add(shooterMonster);

            Monster regularMonster = new Monster(W- S/2 - i *90,H/ 10 * 2, S,S,Color.GREEN);
            this.monsters.add(regularMonster);
            this.getChildren().add(regularMonster);
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
                Iterator<Monster> monsterIterator = this.monsters.iterator();
                while(monsterIterator.hasNext()){
                    Monster monster = monsterIterator.next();
                    if(bullet.getBoundsInParent().intersects(monster.getBoundsInParent())){
                        it.remove();
                        playerBullets.remove(bullet);
                        getChildren().remove(bullet);
                        monster.setHealth(monster.getHealth() - 1);
                        if(monster.getHealth() == 0){
                            monsterIterator.remove();
                            monsters.remove(monster);
                            getChildren().remove(monster);
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
            Iterator<Monster> monsterIterator = this.monsters.iterator();
            while(monsterIterator.hasNext()){
                Monster monster = monsterIterator.next();
                if(monster instanceof ShooterMonster){
                    int bulletX = (int) (monster.getTranslateX() + monster.getWidth() / 2);
                    int bulletY = (int) (monster.getTranslateY() + monsterBulletRadius);

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
        createMonsterBulletAnimation.getKeyFrames().clear();
        updateMonsterBulletAnimation.getKeyFrames().clear();
        createPlayerBulletAnimation.getKeyFrames().clear();
        updatePlayerBulletAnimation.getKeyFrames().clear();
    }

    void debugger(){
        Iterator<Monster> it = monsters.iterator();
        while(it.hasNext()){
            Monster m = it.next();
            System.out.println("X :" + m.getTranslateX() + "Y :" + m.getTranslateY());
        }
    }
}
