package group10.client.api;

import group10.client.controller.LoginController;
import group10.client.model.server.User;
import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class UserApi {

    private static final RestTemplate restTemplate = new RestTemplate();
    private static final String apiAddress = LoginController.apiAddress;

    public static ResponseEntity<JSONObject> signUpUser(String username, String password, String name, String surname, String email){

        String jsonString = new JSONObject()
                .put("username", username)
                .put("password", password)
                .put("name", name)
                .put("surname", surname)
                .put("email", email).toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<String> entity = new HttpEntity<>(jsonString, headers);

        return restTemplate.exchange(apiAddress + "/sign_up",
                HttpMethod.POST,
                entity,
                JSONObject.class);

    }

    public static String loginUser(String username, String password){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("username",username);
        map.add("password",password);

        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(map, headers);

        return restTemplate.exchange( apiAddress + "/login",
                HttpMethod.POST,
                entity,
                String.class).toString();
    }

    public static User getUser(String username){
        return restTemplate.getForObject(apiAddress + "/usernames/"+username , User.class);
    }

}
