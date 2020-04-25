package group10.server.api;

import group10.server.service.ScoreService;
import group10.server.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class that redirects all GET,POST,UPDATE and DELETE requests to {@link ScoreService} .
 */

@RestController
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    /**
     * This method redirects GET request to {@link ScoreService#getAllScores()}
     * @return scores List of all scores in scoreRepository.
     */

    @GetMapping("/scores")
    public List<Score> getScores(){
        return scoreService.getAllScores();
    }

    /**
     * This method redirects GET request to {@link ScoreService#getAllScoresWeekly()}
     * @return scores List of all scores created in the past week in scoreRepository.
     */

    @GetMapping("/scores/weekly")
    public List<Score> getScoresWeekly(){
        return scoreService.getAllScoresWeekly();
    }

    /**
     * This method redirects GET request to {@link ScoreService#getAllScoresMonthly()}
     * @return scores List of all scores created in the past month in scoreRepository.
     */

    @GetMapping("/scores/monthly")
    public List<Score> getScoresMonthly(){
        return scoreService.getAllScoresMonthly();
    }

    /**
     * This method redirects GET request to {@link ScoreService#getAllScoresAllTime()}
     * @return scores List of all scores created in the all time in scoreRepository.
     */

    @GetMapping("/scores/alltime")
    public List<Score> getScoresAllTime(){
        return scoreService.getAllScoresAllTime();
    }

    /**
     * This method redirects GET request to {@link ScoreService#getScore(Long)}
     * @param scoreId id of score object
     * @return Score score object
     */

    @GetMapping("/scores/{scoreId}")
    public Score getScoresById(@PathVariable(value = "scoreId") Long scoreId) { return scoreService.getScore(scoreId); }

    /**
     * This method redirects POST request to {@link ScoreService#addScore(Score, Long)}
     * @param score score information
     * @param userId id of the user
     */
    @PostMapping("/scores/{userId}")
    public void createScore(@Valid @RequestBody Score score, @PathVariable(value = "userId") Long userId) {
        scoreService.addScore(score,userId);
    }

    /**
     * This method redirects DELETE request to {@link ScoreService#deleteScore(Long)}
     * @param scoreId id of the score object to be deleted.
     */
    @DeleteMapping("/scores/{scoreId}")
    public void deleteScore(@PathVariable(value = "scoreId") Long scoreId){
        scoreService.deleteScore(scoreId);
    }

}
