package group10.server.api;

import group10.server.model.Match;
import group10.server.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class MatchController {

    private final MatchService matchService;

    @Autowired
    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/matches")
    public List<Match> getMatches(){
        return matchService.getAllMatches();
    }

    @PutMapping("/matches/{id}")
    public void updateMatch(@RequestBody Match match, @PathVariable(value = "id") Long matchId) {
        matchService.updateMatch(matchId,match);
    }

    @PostMapping("/add_match")
    public void createMatch(@Valid @RequestBody Match match) {
        matchService.addMatch(match);
    }

    @DeleteMapping("/matches/{id}")
    public void deleteMatch(@PathVariable(value = "id") Long matchId){
        matchService.deleteMatch(matchId);
    }

}
