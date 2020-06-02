package group10.server.service;

import group10.server.model.Match;
import group10.server.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Concrete class which implements {@link MatchService} interface.
 */
@Service
public class MatchServiceImpl implements MatchService {
    /* Match Database */
    private final MatchRepository matchRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    @Override
    public List<Match> getAllMatches(){
        return matchRepository.findAll();
    }

    @Override
    public void addMatch(Match match){
        matchRepository.save(match);
    }

    @Override
    public void updateMatch(Long matchId, Match match) {
        /* retrieve all matches from database */
        List<Match> matches = getAllMatches();
        /* get the first match from matches list */
        Match m = matches.get(0);
        /* modify related fields */
        m.setServerUsername(match.getServerUsername());
        m.setServerIP(match.getServerIP());
        m.setServerPort(match.getServerPort());
        m.setServerStatus(match.getServerStatus());
        m.setClientUsername(match.getClientUsername());
        m.setClientStatus(match.getClientStatus());
        /* save changes to the database.*/
        matchRepository.save(m);
    }

    @Override
    public void deleteMatch(Long matchId) {
        matchRepository.deleteById(matchId);
    }
}
