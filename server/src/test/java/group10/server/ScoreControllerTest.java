package group10.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.model.Score;
import group10.server.model.User;
import org.apache.commons.lang.StringUtils;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.lang.reflect.Array;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class ScoreControllerTest extends ServerTest{

    @Test
    public void addNewScoreThenGet() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername4\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test4@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername4");
        assertTrue(user.isPresent());

        User testUser = user.get();

        /* Get the created user with get /users/{id} method */
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Convert returned user from json to string */
        String userString =  result.getResponse().getContentAsString();

        /* Create post request for new score with created user's id */
        MockHttpServletRequestBuilder postScoreRequest = MockMvcRequestBuilders.post("/scores/"+testUser.getId().toString())
                .content("{\"user\":"+ userString +"," +
                        "\"score\":100," +
                        "\"createdAt\":\"2020-03-28\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postScoreRequest).andExpect(status().isOk());

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void getAllScores() throws Exception{

        /* Run get /scores method to get all scores */
        mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllScoresWeekly() throws Exception{

        /* Run get /scores/weekly method to get all scores created in last week */
        mockMvc.perform(MockMvcRequestBuilders.get("/scores/weekly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllScoresMonthly() throws Exception{

        /* Run get /scores/monthly method to get all scores created in last month */
        mockMvc.perform(MockMvcRequestBuilders.get("/scores/monthly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void addNewScoreThenDelete() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername5\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test5@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername5");
        assertTrue(user.isPresent());

        User testUser = user.get();

        /* Get the newly created user with /users/{id} get method */
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Convert returned user from json to string */
        String userString =  result.getResponse().getContentAsString();

        /* Create post request for new score with created user's id */
        MockHttpServletRequestBuilder postScoreRequest = MockMvcRequestBuilders.post("/scores/"+testUser.getId().toString())
                .content("{\"user\":"+ userString +"," +
                        "\"score\":100," +
                        "\"createdAt\":\"2020-03-28\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postScoreRequest).andExpect(status().isOk());

        /* Get scores with get /scores method */
        MvcResult resultScore = mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Convert returned user from json to string */
        String scores = resultScore.getResponse().getContentAsString();

        /* Find the scoreId in returned scores */
        String scoreId = StringUtils.substringBetween(scores, "scoreId\":", ",");

        /* Delete the score */
        mockMvc.perform(MockMvcRequestBuilders.delete("/scores/"+scoreId)).andExpect(status().isOk());

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void testScoreNotFoundException() throws Exception {

        /* Find whether score with id=100 exists */
        Optional<Score> score = scoreRepository.findById(100L);

        /* If score with id=100 exists, delete it*/
        if (score.isPresent()){
            mockMvc.perform(MockMvcRequestBuilders.delete("/scores/100")).andExpect(status().isOk());
        }

        /* Get score with id=100 and expect not found error */
        mockMvc.perform(MockMvcRequestBuilders.get("/scores/100")).andExpect(status().isNotFound());

    }

    @Test
    public void testBadRequestException() throws Exception{

        /* Run get /scores/{id} method with a string instead of long and expect bad request*/
        mockMvc.perform(MockMvcRequestBuilders.get("/scores/random_string")).andExpect(status().isBadRequest());

    }

}
