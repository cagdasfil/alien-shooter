package group10.client.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class LeaderboardController implements Initializable {

    private RestTemplate restTemplate;
    @Value("${spring.application.apiAddress}") private String apiAddress;

    private final ObservableList<ScoreRow> scoresWeekly = FXCollections.observableArrayList();
    private final ObservableList<ScoreRow> scoresMonthly = FXCollections.observableArrayList();

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        restTemplate = new RestTemplate();
        try {
            initializeWeeklyTable();
            initializeMonthlyTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void initializeWeeklyTable() throws JsonProcessingException {

        rankColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        usernameColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Username"));
        scoreColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumnWeekly.setCellValueFactory(new PropertyValueFactory<>("Date"));

        String scoresString = restTemplate.getForObject("http://localhost:8080/scores/weekly" , String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<Score> scores = mapper.readValue(scoresString, new TypeReference<List<Score>>(){});

        for (int rank=0; rank<scores.size(); rank++){
            Score score = scores.get(rank);
            scoresWeekly.add(new ScoreRow(rank+1, score.getUser().getUsername(), score.getScore(), score.getCreatedAt()));
        }

        tableWeekly.setItems(scoresWeekly);
    }

    public void initializeMonthlyTable() throws JsonProcessingException {

        rankColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Rank"));
        usernameColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Username"));
        scoreColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Score"));
        dateColumnMonthly.setCellValueFactory(new PropertyValueFactory<>("Date"));

        String scoresString = restTemplate.getForObject("http://localhost:8080/scores/monthly" , String.class);

        ObjectMapper mapper = new ObjectMapper();
        List<Score> scores = mapper.readValue(scoresString, new TypeReference<List<Score>>(){});

        for (int rank=0; rank<scores.size(); rank++){
            Score score = scores.get(rank);
            scoresMonthly.add(new ScoreRow(rank+1, score.getUser().getUsername(), score.getScore(), score.getCreatedAt()));
        }

        tableMonthly.setItems(scoresMonthly);
    }

    @FXML
    public void backClick() throws IOException {
        Parent gameLobby = FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader().getResource("GameLobby.fxml")));
        generalLayout.getChildren().setAll(gameLobby);
    }

}