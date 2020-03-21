package group10.server.service;

import group10.server.model.Score;
import group10.server.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    @Override
    public void addScore(Score score){
        scoreRepository.save(score);
    }

    @Override
    public Score getScore(Long scoreId ){
        Optional<Score> scores = scoreRepository.findById(scoreId);
        return scores.get();
    }

    @Override
    public void deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
    }
}
