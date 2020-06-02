package group10.server.service;

import group10.server.model.Match;

import java.util.List;

/**
 * Service which handles all requests directed from {@link group10.server.api.MatchController} .
 */
public interface MatchService {

    /**
     * This method returns all matches in matchRepository.
     * @return matches List of all matches in matchRepository.
     */
    List<Match> getAllMatches();

    /**
     * This method adds match object to matchRepository with given parameter match.
     * @param match match information
     */
    void addMatch(Match match);

    /**
     * This method updates user information with
     * @param matchId id of match object to be updated.
     * @param match information about match which will be changed with existing information.
     */
    void updateMatch(Long matchId, Match match);

    /**
     * This method deletes match with given matchId from matchRepository.
     * @param matchId id of match object to be deleted.
     */
    void deleteMatch(Long matchId);

}
