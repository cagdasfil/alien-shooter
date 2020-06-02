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

    /**
     *
     * @param playerX x coordinate of the player
     * @param playerY y coordinate of the player
     */
    public GameData(Double playerX, Double playerY) {
        this.playerX = playerX;
        this.playerY = playerY;
        this.isGameFinished = false;
    }

    /**
     *
     * @param playerX x coordinate of the player
     * @param playerY y cooordinate of the player
     * @param bossX x coordinate of the boss
     * @param bossHit number of boss hits
     * @param playerHealth remaining health of the player
     */

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

    /**
     * Getter method to be able to access x coordinate of the player.
     * @return
     */
    public Double getPlayerX() {
        return playerX;
    }


    /**
     * Getter method to be able to access y coordinate of the player
     * @return
     */
    public Double getPlayerY() {
        return playerY;
    }


    /**
     * Getter method to be able to access number of hits for the player
     * @return
     */
    public Integer getBossHit() {
        return bossHit;
    }


    /**
     * Getter method to be able to access remaining health of the player
     * @return
     */
    public Integer getPlayerHealth() {
        return playerHealth;
    }

    /**
     * Getter method to be able to understand game is finished or not
     * @return
     */
    public Boolean getGameFinished() {
        return isGameFinished;
    }


    /**
     * Getter method to be able to access x coordinate of the boss.
     * @return
     */
    public Double getBossX() {
        return bossX;
    }
}
