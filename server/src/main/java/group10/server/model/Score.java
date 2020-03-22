package group10.server.model;

import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="score")
public class Score {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="score_id")
    private Long scoreId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name="score", nullable = false, updatable = false)
    private Long score;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    public Score() {

    }

    public Score(User user, Long score, LocalDate createdAt) {
        this.user = user;
        this.score = score;
        this.createdAt = createdAt;
    }

    public Long getScoreId() {
        return scoreId;
    }

    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
