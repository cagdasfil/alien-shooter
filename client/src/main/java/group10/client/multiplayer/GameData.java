package group10.client.multiplayer;

public class GameData {
    // x coordinate of the player
    Double xCoordinate;

    // y coordinate of the player
    Double yCoordinate;

    // number of hits to the boss
    Integer bossHit;

    //remaining health of the player
    Integer playerHealth;

    // flag whether game is finished or not
    Boolean isGameFinished;

    public GameData() {

    }

    public GameData(Double xCoordinate, Double yCoordinate) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.isGameFinished = false;
    }

    public GameData(Double xCoordinate, Double yCoordinate, Integer bossHit, Integer playerHealth) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.bossHit = bossHit;
        this.playerHealth = playerHealth;
        this.isGameFinished = false;
    }

    public GameData(Boolean isGameFinished) {
        this.isGameFinished = isGameFinished;
    }

    public Double getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Double xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Double getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Double yCoordinate) {
        this.yCoordinate = yCoordinate;
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
}
