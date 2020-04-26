package group10.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.client.api.ScoreApi;
import group10.client.model.client.ScoreRow;
import group10.client.model.server.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

/**
 * This class is controller for the Leaderboard.fxml file
 */
public class LeaderboardController implements Initializable {

    private final ObservableList<ScoreRow> scoresWeekly = FXCollections.observableArrayList();
    private final ObservableList<ScoreRow> scoresMonthly = FXCollections.observableArrayList();
    private final ObservableList<ScoreRow> scoresAllTime = FXCollections.observableArrayList();

    @FXML public AnchorPane generalLayout;
    @FXML private TableView<ScoreRow> tableWeekly;
    @FXML public TableColumn<ScoreRow, Long> rankColumnWeekly;
    @FXML public TableColumn<ScoreRow, String> usernameColumnWeekly;
    @FXML public TableColumn<ScoreRow, Long> scoreColumnWeekly;
    @FXML public TableColumn<ScoreRow, LocalDate> dateColumnWeekly;
    @FXML private TableView<ScoreRow> tableMonthly;
    @FXML public TableColumn<ScoreRow, Long> rankColumnMonthly;
    @FXML public TableColumn<ScoreRow, String> usernameColumnMonthly;
    @FXML public TableColumn<ScoreRow, Long> scoreColumnMonthly;
    @FXML public TableColumn<ScoreRow, LocalDate> dateColumnMonthly;
    @FXML private TableView<ScoreRow> tableAllTime;
    @FXML public TableColumn<ScoreRow, Long> rankColumnAllTime;
    @FXML public TableColumn<ScoreRow, String> usernameColumnAllTime;
    @FXML public TableColumn<ScoreRow, Long> scoreColumnAllTime;
    @FXML public TableColumn<ScoreRow, LocalDate> dateColumnAllTime;

    /**
     * Initialization function of the controller.
     * @param url url for initialization
     * @param resourceBundle resources to bundle
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        try {
            initializeWeeklyTable();
            initializeMonthlyTable();
            initializeAllTimeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This method gets weekly scores and sends to the fillTable method.
     * @throws JsonProcessingException exception for processing returned JSON object
     */
    public void initializeWeeklyTable() throws JsonProcessingException {

        rankColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        usernameColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Username"));
        scoreColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Date"));

        String scoresString = ScoreApi.getScoresWeekly();

        fillTable(scoresString, scoresWeekly, tableWeekly);
    }

    /**
     * This method gets monthly scores and sends to the fillTable method.
     * @throws JsonProcessingException exception for processing returned JSON object
     */
    public void initializeMonthlyTable() throws JsonProcessingException {

        rankColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        usernameColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Username"));
        scoreColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Date"));

        String scoresString = ScoreApi.getScoresMonthly();

        fillTable(scoresString, scoresMonthly, tableMonthly);
    }

    /**
     * This method gets all scores and sends to the fillTable method.
     * @throws JsonProcessingException exception for processing returned JSON object
     */
    public void initializeAllTimeTable() throws JsonProcessingException {

        rankColumnAllTime.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        usernameColumnAllTime.setCellValueFactory(new PropertyValueFactory<>("Username"));
        scoreColumnAllTime.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumnAllTime.setCellValueFactory(new PropertyValueFactory<>("Date"));

        String scoresString = ScoreApi.getScoresAllTime();

        fillTable(scoresString, scoresAllTime, tableAllTime);
    }

    /**
     * This method adds scores to the specified table
     * @param scoresString list of scores as String
     * @param scoresList list of the score rows to add table
     * @param table table to add score rows
     * @throws JsonProcessingException exception for processing returned JSON object
     */
    private void fillTable(String scoresString, ObservableList<ScoreRow> scoresList, TableView<ScoreRow> table) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        List<Score> scores = mapper.readValue(scoresString, new TypeReference<>(){});

        for (int rank=0; rank<scores.size(); rank++){
            Score score = scores.get(rank);
            scoresList.add(new ScoreRow(rank+1, score.getUser().getUsername(), score.getScore(), score.getCreatedAt()));
        }

        table.setItems(scoresList);
    }

    /**
     * Controller function of the back link click
     * @throws IOException exception for FXML load operation
     */
    @FXML
    public void backClick() throws IOException {
        Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
        generalLayout.getChildren().setAll(gameLobby);
    }

}
