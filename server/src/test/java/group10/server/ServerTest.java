package group10.server;

import group10.server.repository.RoleRepository;
import group10.server.repository.ScoreRepository;
import group10.server.repository.UserRepository;
import lombok.extern.java.Log;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ServerTest {

    @Autowired
    WebApplicationContext wac;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ScoreRepository scoreRepository;

    @Autowired
    RoleRepository roleRepository;

    MockMvc mockMvc;

    @Before
    public void setup(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    public JSONObject stringToJSON(String string) {
        try {
            JSONObject jsonObject = new JSONObject(string);
            return jsonObject;
        } catch (JSONException err) {
            System.out.println(err.toString());
        }
        return null;
    }
}
