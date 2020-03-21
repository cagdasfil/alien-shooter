package group10.server.Score;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name="score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="score_id")
    private Long scoreId;

    @Column(name ="username", nullable = false, updatable = false)
    private String username;

    @Column(name="score", nullable = false, updatable = false)
    private Long score;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private Date createdAt;

    public Score() {

    }

    public Score(Long scoreId, String username, Long score, Date createdAt) {
        this.scoreId = scoreId;
        this.username = username;
        this.score = score;
        this.createdAt = createdAt;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
