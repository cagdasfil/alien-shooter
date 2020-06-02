package group10.server.service;

import group10.server.model.Match;
import java.util.List;

public interface MatchService {

    List<Match> getAllMatches();

    void addMatch(Match match);

    void updateMatch(Long matchId, Match match);

    void deleteMatch(Long matchId);

}
