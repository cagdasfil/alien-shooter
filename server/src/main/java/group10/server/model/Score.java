package group10.server.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Column(name="score", nullable = false, updatable = false)
    private Long score;

    @Column(name = "created_at", nullable = false)
    @CreatedDate
    private LocalDate createdAt;

    /**
     * Empty class constructor
     */
    public Score() {

    }

    /**
     * @param user   user object to set
     * @param score   score to set
     * @param createdAt   date to set
     */
    public Score(User user, Long score, LocalDate createdAt) {
        this.user = user;
        this.score = score;
        this.createdAt = createdAt;
    }

    /**
     * @return scoreId   scoreId of the score object
     */
    public Long getScoreId() {
        return scoreId;
    }

    /**
     * @return user   user of the score object
     */
    public User getUser() {
        return user;
    }

    /**
     * @return score   score of the score object
     */
    public Long getScore() {
        return score;
    }

    /**
     * @return createdAt   createdAt of the score object
     */
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * @param scoreId   the scoreId to set
     */
    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * @param user   the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @param score   the score to set
     */
    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * @param createdAt   the createdAt to set
     */
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
