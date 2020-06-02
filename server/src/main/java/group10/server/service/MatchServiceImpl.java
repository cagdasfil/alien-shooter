package group10.server.service;

import group10.server.model.Match;
import group10.server.repository.MatchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Concrete class which implements {@link UserService} interface.
 */

@Service
public class MatchServiceImpl implements MatchService {
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
        /* check whether the user with the given id is in database or not*/
        List<Match> matches = getAllMatches();
        Match m = matches.get(0);  // find the user that will be modified by id's.
        /* modify related fields*/
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
