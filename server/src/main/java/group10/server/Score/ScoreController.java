package group10.server.Score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @GetMapping("/scores")
    public List<Score> getUsers(){
        return scoreService.getAllScores();
    }

    @GetMapping("/scores/{scoreId}")
    public Score getScoresById(@PathVariable(value = "scoreId") Long scoreId) { return scoreService.getScore(scoreId); }

    @PostMapping("/scores")
    public void createScore(@Valid @RequestBody Score score) {
        scoreService.addScore(score);
    }

    @DeleteMapping("/users/{scoreId}")
    public void deleteScore(@PathVariable(value = "scoreId") Long scoreId){
        scoreService.deleteScore(scoreId);
    }

}
