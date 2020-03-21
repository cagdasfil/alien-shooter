package group10.server.Score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    public void addScore(Score score){
        scoreRepository.save(score);
    }

    public Score getScore(Long scoreId ){
        Optional<Score> scores = scoreRepository.findById(scoreId);
        return scores.get();
    }

    public void deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
    }
}
