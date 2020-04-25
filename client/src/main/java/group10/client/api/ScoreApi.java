package group10.client.api;

import group10.client.controller.LoginController;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ScoreApi {

    private static final RestTemplate restTemplate = new RestTemplate();
    @Value("${spring.application.apiAddress}") private static String apiAddress;

    public static String getScoresWeekly(){
        return restTemplate.getForObject("http://localhost:8080/scores/weekly" , String.class);
    }

    public static String getScoresMonthly(){
        return restTemplate.getForObject("http://localhost:8080/scores/monthly" , String.class);
    }

    public static void saveScore(Integer score){
            String jsonString = new JSONObject()
                    .put("score", score).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

            restTemplate.exchange("http://localhost:8080/scores/"+ LoginController.user.getId().toString(),
                    HttpMethod.POST,
                    entity,
                    JSONObject.class);
    }

}
