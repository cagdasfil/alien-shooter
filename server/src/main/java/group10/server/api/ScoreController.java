package group10.server.api;

import group10.server.service.ScoreServiceImpl;
import group10.server.model.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ScoreController {

    @Autowired
    private ScoreServiceImpl scoreServiceImpl;

    @GetMapping("/scores")
    public List<Score> getUsers(){
        return scoreServiceImpl.getAllScores();
    }

    @GetMapping("/scores/{scoreId}")
    public Score getScoresById(@PathVariable(value = "scoreId") Long scoreId) { return scoreServiceImpl.getScore(scoreId); }

    @PostMapping("/scores")
    public void createScore(@Valid @RequestBody Score score) {
        scoreServiceImpl.addScore(score);
    }

    @DeleteMapping("/scores/{scoreId}")
    public void deleteScore(@PathVariable(value = "scoreId") Long scoreId){
        scoreServiceImpl.deleteScore(scoreId);
    }

}
