package group10.client.model.server;

public class Score {

    private Long scoreId;
    private User user;
    private Long score;
    private String createdAt;

    /**
     * Getter method for scoreId attribute of Score object.
     * @return scoreId   scoreId of the score object
     */
    public Long getScoreId() {
        return scoreId;
    }

    /**
     * Getter method for user attribute of Score object.
     * @return user   user of the score object
     */

    public User getUser() {
        return user;
    }

    /**
     * Getter method for score attribute of Score object.
     * @return score   score of the score object
     */
    public Long getScore() {
        return score;
    }

    /**
     * Getter method for createdAt attribute of Score object.
     * @return createdAt   createdAt of the score object
     */
    public String getCreatedAt() {
        return createdAt;
    }
}
