package group10.client.model.server;

import java.time.LocalDate;

public class Score {

    private Long scoreId;
    private User user;
    private Long score;
    private String createdAt;

    public Long getScoreId() {
        return scoreId;
    }

    public User getUser() {
        return user;
    }

    public Long getScore() {
        return score;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
