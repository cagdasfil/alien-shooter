package group10.server.service;

import group10.server.exception.ApiException;
import group10.server.model.Score;

import java.util.List;


/**
 * Service which handles all requests directed from {@link group10.server.api.ScoreController} .
 */


public interface ScoreService {

    /**
     * This method returns all scores in scoreRepository.
     * @return scores List of all scores in scoreRepository.
     */

    List<Score> getAllScores();

    /**
     * This method returns all scores created in the past week.
     * @return scores List of all scores created in the past week in scoreRepository.
     */


    List<Score> getAllScoresWeekly();

    /**
     * This method returns all scores created in the past month.
     * @return scores List of all scores created in the past month in scoreRepository
     */

    List<Score> getAllScoresMonthly();

    /**
     * This method returns all scores created in all time.
     * @return scores List of all scores created in all time in scoreRepository.
     */

    List<Score> getAllScoresAllTime();

    /**
     * This method adds score to the score database for the user with given id.
     * @param score score information
     * @param userId id of the user
     */
    void addScore(Score score, Long userId);

    /**
     * This method returns score object with given id.
     * @param scoreId id of score object
     * @throws ApiException.ScoreNotFound when the score object with the given id does not exists in database.
     * @return Score score object
     */
    Score getScore(Long scoreId );

    /**
     * This method deletes score object with given id.
     * @throws ApiException.ScoreNotFound when the score object with the given id does not exists in database.
     * @param scoreId id of score object to be deleted.
     */
    void deleteScore(Long scoreId);
}
