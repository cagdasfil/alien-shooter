package group10.server.api;

import group10.server.model.Match;
import group10.server.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller class that redirects all GET,POST,UPDATE and DELETE requests to {@link MatchService} .
 */
@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    /**
     * This method redirects GET request to {@link MatchService#getAllMatches()}
     * @return matches List of all matches in userRepository.
     */
    @GetMapping("/matches")
    public List<Match> getMatches(){
        return matchService.getAllMatches();
    }

    /**
     * This method redirects UPDATE request to {@link MatchService#updateMatch(Long, Match)}
     * @param match information about match which will be changed with existing information.
     * @param matchId id of match to be updated
     */
    @PutMapping("/matches/{id}")
    public void updateMatch(@RequestBody Match match, @PathVariable(value = "id") Long matchId) {
        matchService.updateMatch(matchId,match);
    }

    /**
     * This method redirects POST request to {@link MatchService#addMatch(Match)}
     * @param match match information
     */
    @PostMapping("/add_match")
    public void createMatch(@Valid @RequestBody Match match) {
        matchService.addMatch(match);
    }

    /**
     * This method redirects DELETE request to {@link MatchService#deleteMatch(Long)}
     * @param matchId id of match to be deleted.
     */
    @DeleteMapping("/matches/{id}")
    public void deleteMatch(@PathVariable(value = "id") Long matchId){
        matchService.deleteMatch(matchId);
    }

}
