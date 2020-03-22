package group10.server.service;

import group10.server.model.Score;
import group10.server.model.User;
import group10.server.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ScoreServiceImpl implements ScoreService {

    private final ScoreRepository scoreRepository;
    private final UserService userService;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, UserService userService) {
        this.scoreRepository = scoreRepository;
        this.userService = userService;
    }

    @Override
    public List<Score> getAllScores(){
        return scoreRepository.findAll();
    }

    @Override
    public void addScore(Score score, Long userId){
        //find given user by ID and set in score table as foreign key.
        User u = userService.getUser(userId);
        score.setUser(u);

        //get current Date and set in score table.
        java.util.Date date = new java.util.Date();
        score.setCreatedAt(date);

        scoreRepository.save(score);
    }

    @Override
    public Score getScore(Long scoreId ){
        Optional<Score> score = scoreRepository.findById(scoreId);

        if(score.isPresent()){
            return score.get();
        }
        else{
            throw new EntityNotFoundException();
        }
    }

    @Override
    public void deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
    }
}
