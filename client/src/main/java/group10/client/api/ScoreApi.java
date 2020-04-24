package group10.client.api;

import org.springframework.beans.factory.annotation.Value;
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

}
