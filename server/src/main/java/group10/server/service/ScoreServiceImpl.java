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


/**
 * Concrete class which implements {@link ScoreService} interface.
 */

@Service
public class ScoreServiceImpl implements ScoreService {
    /* Score Database */
    private final ScoreRepository scoreRepository;
    /* User Database */
    private final UserService userService;

    @Autowired
    public ScoreServiceImpl(ScoreRepository scoreRepository, UserService userService) {
        this.scoreRepository = scoreRepository;
        this.userService = userService;
    }

    @Override
    public List<Score> getAllScores(){
        /* return all scores in database*/
        return scoreRepository.findAll();
    }

    @Override
    public List<Score> getAllScoresWeekly(){
        LocalDate date = LocalDate.now();
        /* return all scores created in the past week */
        return scoreRepository.findAllWeekly(date.minusDays(7));
    }

    @Override
    public List<Score> getAllScoresMonthly(){
        LocalDate date = LocalDate.now();
        /* return all scores created in the past week */
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
        /* add score to database */
        scoreRepository.save(score);
    }

    @Override
    public Score getScore(Long scoreId ){
        /* find the score with given id*/
        Optional<Score> score = scoreRepository.findById(scoreId);
        /* check whether the score is in database or not*/
        if(score.isPresent()){
            /* return the score if exists*/
            return score.get();
        }
        else{
            /* throw ScoreNotFound exception if the score cannot found in database*/
            throw new ApiException.ScoreNotFound("Score does not exist with the given ID :",scoreId);
        }
    }

    @Override
    public void deleteScore(Long scoreId) {
        /* find the score with given id*/
        Optional<Score> score = scoreRepository.findById(scoreId);
        /* check whether the score is in database or not*/
        if(score.isPresent()){
            /* delete the score if exists*/
            scoreRepository.deleteById(scoreId);
        }
        else{
            /* throw ScoreNotFound exception if the score cannot found in database*/
            throw new ApiException.ScoreNotFound("Score does not exist with the given ID :",scoreId);
        }
    }
}
