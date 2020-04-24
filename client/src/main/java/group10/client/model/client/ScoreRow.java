package group10.client.model.client;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

public class ScoreRow {

    private SimpleIntegerProperty rank;
    private SimpleStringProperty username;
    private SimpleLongProperty score;
    private SimpleStringProperty  date;

    public ScoreRow(Integer rank, String username, Long score, String date) {
        this.rank = new SimpleIntegerProperty(rank);
        this.username = new SimpleStringProperty(username);
        this.score = new SimpleLongProperty(score);
        this.date = new SimpleStringProperty(date);
    }

    public int getRank() {
        return rank.get();
    }

    public SimpleIntegerProperty rankProperty() {
        return rank;
    }

    public String getUsername() {
        return username.get();
    }

    public SimpleStringProperty usernameProperty() {
        return username;
    }

    public long getScore() {
        return score.get();
    }

    public SimpleLongProperty scoreProperty() {
        return score;
    }

    public String getDate() {
        return date.get();
    }

    public SimpleStringProperty dateProperty() {
        return date;
    }

}
