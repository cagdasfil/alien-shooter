package group10.server.service;

import group10.server.model.Score;

import java.util.List;

public interface ScoreService {
    List<Score> getAllScores();
    List<Score> getAllScoresWeekly();
    List<Score> getAllScoresMonthly();
    void addScore(Score score, Long userId);
    Score getScore(Long scoreId );
    void deleteScore(Long scoreId);
}
