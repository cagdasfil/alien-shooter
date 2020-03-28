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
     * Empty class constructor for Score class.
     */
    public Score() {

    }

    /**
     * Constructor for Score class.
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
    public LocalDate getCreatedAt() {
        return createdAt;
    }

    /**
     * Setter method for scoreId attribute of Score object. Sets scoreId of Score object with given parameter.
     * @param scoreId   the scoreId to set
     */
    public void setScoreId(Long scoreId) {
        this.scoreId = scoreId;
    }

    /**
     * Setter method for user attribute of Score object. Sets user of Score object with given parameter.
     * @param user   the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Setter method for score attribute of Score object. Sets score of Score object with given parameter.
     * @param score   the score to set
     */
    public void setScore(Long score) {
        this.score = score;
    }

    /**
     * Setter method for createdAt attribute of Score object. Sets createdAt of Score object with given parameter.
     * @param createdAt   the createdAt to set
     */
    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }
}
