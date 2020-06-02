package group10.client.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.client.controller.LoginController;
import group10.client.model.server.Match;
import group10.client.model.server.Score;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * This class runs user related HTTP methods.
 */
public class MatchApi {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String apiAddress = LoginController.apiAddress;

    public static ResponseEntity<JSONObject> addMatch(){

        String serverIP = "";
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            serverIP = in.readLine(); //you get the IP as a String
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String username = LoginController.user.getUsername();
        String serverPort = "9876";

        String jsonString = new JSONObject()
                .put("serverUsername", username)
                .put("serverIP", serverIP)
                .put("serverPort", serverPort)
                .put("serverStatus", "wait")
                .put("clientUsername", "")
                .put("clientStatus", "wait").toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        return restTemplate.exchange(apiAddress + "/add_match",
                HttpMethod.POST,
                entity,
                JSONObject.class);

    }

    public static Match getMatch() throws JsonProcessingException {
        String s = restTemplate.getForObject(apiAddress + "/matches" , String.class);
        ObjectMapper mapper = new ObjectMapper();
        List<Match> matches = mapper.readValue(s, new TypeReference<List<Match>>(){});
        if (matches.isEmpty()){
            return null;
        }
        else {
            return matches.get(0);
        }
    }

    public static void updateMatch(Match match){
        String jsonString = new JSONObject()
                .put("serverUsername", match.getServerUsername())
                .put("serverIP", match.getServerIP())
                .put("serverPort", match.getServerPort())
                .put("serverStatus", match.getServerStatus())
                .put("clientUsername", match.getClientUsername())
                .put("clientStatus", match.getClientStatus()).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        restTemplate.exchange(apiAddress + "/matches/" + match.getId(),
                HttpMethod.PUT,
                entity,
                JSONObject.class);
    }

    public static void deleteMatch(Match match){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>("", headers);

        restTemplate.exchange(apiAddress + "/matches/" + match.getId(),
                HttpMethod.DELETE,
                entity,
                JSONObject.class);
    }

}
