package group10.server;

import group10.server.model.Score;
import group10.server.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class ScoreControllerTest extends ServerTest{

    @Test
    public void getAllScores() throws Exception{

        /* Run get /scores method to get all scores */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of scores as String */
        String scoresString = mvcResult.getResponse().getContentAsString();

        /* Check whether scores exist */
        assertFalse(scoresString.isEmpty());

    }

    @Test
    public void getAllScoresWeekly() throws Exception{

        /* Run get /scores/weekly method to get all scores */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/scores/weekly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of scores as String */
        String scoresString = mvcResult.getResponse().getContentAsString();

        /* Find dates in returned scores */
        Pattern pattern = Pattern.compile("createdAt\":\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(scoresString);

        /* Check whether scores are created in last week */
        while (matcher.find()) {
            LocalDate localDate = LocalDate.parse(matcher.group(1));
            assertTrue(localDate.isAfter(LocalDate.now().minusDays(8)));
        }

    }

    @Test
    public void getAllScoresMonthly() throws Exception{

        /* Run get /scores/monthly method to get all scores */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/scores/monthly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of scores as String */
        String scoresString = mvcResult.getResponse().getContentAsString();

        /* Find dates in returned scores */
        Pattern pattern = Pattern.compile("createdAt\":\"(.*?)\"", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(scoresString);

        /* Check whether scores are created in last month */
        while (matcher.find()) {
            LocalDate localDate = LocalDate.parse(matcher.group(1));
            assertTrue(localDate.isAfter(LocalDate.now().minusDays(31)));
        }

    }

    @Test
    public void getAllScoresAllTime() throws Exception{

        /* Run get /scores/alltime method to get all scores */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/scores/alltime")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of scores as String */
        String scoresString = mvcResult.getResponse().getContentAsString();

        /* Find scores in returned scores */
        Pattern pattern = Pattern.compile("score\":(.*?),", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(scoresString);

        /* Create score variables */
        long highScore = 0L;
        long lowScore = 0L;

        /* Take first returned score */
        if (matcher.find()){
            highScore = Long.parseLong(matcher.group(1));
        }

        /* Check whether scores are returned in order */
        while (matcher.find()) {
            lowScore = Long.parseLong(matcher.group(1));
            assertTrue( highScore >= lowScore );
            highScore = lowScore;
        }

    }

    @Test
    public void addNewScoreThenGetScoreThenDeleteUser() throws Exception{

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
                        "\"createdAt\":\"2020-03-21\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postScoreRequest).andExpect(status().isOk());

        /* Delete the user. Deleting user cascades to delete score */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

        /* Run get /scores method to get all scores */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of scores as String */
        String scoresString = mvcResult.getResponse().getContentAsString();

        /* Check whether there are no scores for specified user */
        assertFalse(scoresString.contains("\"username\":\"testusername4\""));

    }

    @Test
    public void addNewScoreThenDeleteScore() throws Exception{

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
        String scoresString = resultScore.getResponse().getContentAsString();

        /* Find the scoreId of the user in returned*/
        Pattern pattern = Pattern.compile("scoreId\":(.?),\"user\":\\x7b\"id\":\\d+,\"username\":\"testusername5", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(scoresString);
        String scoreId = "1";
        if (matcher.find()){
            scoreId = matcher.group(1);
        }

        /* Delete the score */
        mockMvc.perform(MockMvcRequestBuilders.delete("/scores/"+scoreId)).andExpect(status().isOk());

        /* Get scores with get /scores method again */
        resultScore = mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Convert returned scores from json to string */
        scoresString = resultScore.getResponse().getContentAsString();

        /* Try to find the scoreId of the deleted score*/
        matcher = pattern.matcher(scoresString);
        assertFalse(matcher.find());

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
