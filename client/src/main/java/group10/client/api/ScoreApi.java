package group10.client.api;

import group10.client.controller.LoginController;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class ScoreApi {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String apiAddress = LoginController.apiAddress;

    public static String getScoresWeekly(){
        return restTemplate.getForObject(apiAddress + "/scores/weekly" , String.class);
    }

    public static String getScoresMonthly(){
        return restTemplate.getForObject(apiAddress + "/scores/monthly" , String.class);
    }

    public static void saveScore(Integer score){
            String jsonString = new JSONObject()
                    .put("score", score).toString();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

            restTemplate.exchange(apiAddress + "/scores/"+ LoginController.user.getId().toString(),
                    HttpMethod.POST,
                    entity,
                    JSONObject.class);
    }

}
