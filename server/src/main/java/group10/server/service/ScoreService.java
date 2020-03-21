package group10.server.service;

import group10.server.model.Score;

import java.util.List;

public interface ScoreService {
    List<Score> getAllScores();
    void addScore(Score score);
    Score getScore(Long scoreId );
    void deleteScore(Long scoreId);
}
