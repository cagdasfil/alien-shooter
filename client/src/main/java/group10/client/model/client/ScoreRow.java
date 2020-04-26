package group10.client.model.client;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * This class is used for displaying scores in leaderboard tables. It includes required fields in the table.
 */
public class ScoreRow {

    private SimpleIntegerProperty rank;
    private SimpleStringProperty username;
    private SimpleLongProperty score;
    private SimpleStringProperty  date;

    /**
     *
     * @param rank rank of the player according to score
     * @param username username of the player
     * @param score score of the player
     * @param date creation date of the score
     */
    public ScoreRow(Integer rank, String username, Long score, String date) {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleLongProperty(score);
        this.date = new SimpleStringProperty(date);
    }

    /**
     * Getter method to be able to access the rank of the user score.
     * @return rank of the user score
     */
    public int getRank() {
        return rank.get();
    }

    /**
     * This method returns the rank of the user score as Property.
     * @return rank of the user score as {@link SimpleIntegerProperty}
     */
    public SimpleIntegerProperty rankProperty() {
        return rank;
    }

    /**
     * Getter method to be able to access the username of the score owner.
     * @return username of the user
     */
    public String getUsername() {
        return username.get();
    }

    /**
     * This method returns the username of the score owner.
     * @return rank of the user score as {@link SimpleStringProperty}
     */
    public SimpleStringProperty usernameProperty() {
        return username;
    }

    /**
     * Getter method to be able to access the score.
     * @return rank of the user score
     */
    public long getScore() {
        return score.get();
    }

    /**
     * This method returns the score as Property.
     * @return score as {@link SimpleLongProperty}
     */
    public SimpleLongProperty scoreProperty() {
        return score;
    }

    /**
     * Getter method to be able to access the creation date of the score.
     * @return date of the user score
     */
    public String getDate() {
        return date.get();
    }

    /**
     * This method returns the date of the user score as Property.
     * @return date of the user score as {@link SimpleStringProperty}
     */
    public SimpleStringProperty dateProperty() {
        return date;
    }

}
