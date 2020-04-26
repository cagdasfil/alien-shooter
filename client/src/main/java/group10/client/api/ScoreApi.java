package group10.client.api;

import group10.client.controller.LoginController;
import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

/**
 * This class runs score related HTTP methods.
 */
public class ScoreApi {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String apiAddress = LoginController.apiAddress;

    /**
     * This method returns all scores created in last week.
     * @return weekly scores as JSON string.
     */
    public static String getScoresWeekly(){
        return restTemplate.getForObject(apiAddress + "/scores/weekly" , String.class);
    }

    /**
     * This method returns all scores created in last month.
     * @return monthly scores as JSON string.
     */
    public static String getScoresMonthly(){
        return restTemplate.getForObject(apiAddress + "/scores/monthly" , String.class);
    }

    /**
     * This method returns all scores.
     * @return all scores as JSON string.
     */
    public static String getScoresAllTime(){
        return restTemplate.getForObject(apiAddress + "/scores/alltime" , String.class);
    }

    /**
     * This method saves score of the logged in user.
     * @param score score of the user.
     */
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
