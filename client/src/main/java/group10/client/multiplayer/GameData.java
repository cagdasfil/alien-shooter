package group10.client.multiplayer;

public class GameData {
    // x coordinate of the player
    Double playerX;

    // y coordinate of the player
    Double playerY;

    // x coordinate of the boss
    Double bossX;

    // number of hits to the boss
    Integer bossHit;

    //remaining health of the player
    Integer playerHealth;

    // flag whether game is finished or not
    Boolean isGameFinished;

    public GameData() {

    }

    public GameData(Double playerX, Double playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.isGameFinished = false;
    }

    public GameData(Double playerX, Double playerY,Double bossX, Integer bossHit, Integer playerHealth) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.bossX = bossX;
        this.bossHit = bossHit;
        this.playerHealth = playerHealth;
        this.isGameFinished = false;
    }

    public GameData(Boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public Double getPlayerX() {
        return playerX;
    }

    public void setPlayerX(Double playerX) {
        this.playerX = playerX;
    }

    public Double getPlayerY() {
        return playerY;
    }

    public void setPlayerY(Double playerY) {
        this.playerY = playerY;
    }

    public Integer getBossHit() {
        return bossHit;
    }

    public void setBossHit(Integer bossHit) {
        this.bossHit = bossHit;
    }

    public Integer getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(Integer playerHealth) {
        this.playerHealth = playerHealth;
    }

    public Boolean getGameFinished() {
        return isGameFinished;
    }

    public void setGameFinished(Boolean gameFinished) {
        isGameFinished = gameFinished;
    }

    public Double getBossX() {
        return bossX;
    }

    public void setBossX(Double bossX) {
        this.bossX = bossX;
    }
}
