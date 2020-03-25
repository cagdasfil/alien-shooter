package group10.server.service;

import group10.server.exception.ApiException;
import group10.server.model.Score;
import group10.server.model.User;
import group10.server.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
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
    public List<Score> getAllScoresWeekly(){
        LocalDate date = LocalDate.now();
        return scoreRepository.findAllWeekly(date.minusDays(7));
    }

    @Override
    public List<Score> getAllScoresMonthly(){
        LocalDate date = LocalDate.now();
        return scoreRepository.findAllMonthly(date.minusDays(30));
    }

    @Override
    public void addScore(Score score, Long userId){
        //find given user by ID and set in score table as foreign key.
        User u = userService.getUser(userId);
        score.setUser(u);

        //get current Date and set in score table.
        LocalDate date = LocalDate.now();
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
            throw new ApiException.ScoreNotFound("Score does not exist with the given ID :",scoreId);
        }
    }

    @Override
    public void deleteScore(Long scoreId) {
        scoreRepository.deleteById(scoreId);
    }
}
