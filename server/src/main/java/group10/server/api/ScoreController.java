package group10.server.api;

import group10.server.service.ScoreService;
import group10.server.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ScoreController {

    private final ScoreService scoreService;

    @Autowired
    public ScoreController(ScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/scores")
    public List<Score> getScores(){
        return scoreService.getAllScores();
    }

    @GetMapping("/scores/{scoreId}")
    public Score getScoresById(@PathVariable(value = "scoreId") Long scoreId) { return scoreService.getScore(scoreId); }

    @PostMapping("/scores/{userId}")
    public void createScore(@Valid @RequestBody Score score, @PathVariable(value = "userId") Long userId) {
        scoreService.addScore(score,userId);
    }

    @DeleteMapping("/scores/{scoreId}")
    public void deleteScore(@PathVariable(value = "scoreId") Long scoreId){
        scoreService.deleteScore(scoreId);
    }

}
