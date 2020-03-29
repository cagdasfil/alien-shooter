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

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername4\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test4@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        Optional<User> user = userRepository.findByUsername("testusername4");
        assertTrue(user.isPresent());

        User testUser = user.get();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String userString =  result.getResponse().getContentAsString();

        MockHttpServletRequestBuilder postScoreRequest = MockMvcRequestBuilders.post("/scores/"+testUser.getId().toString())
                .content("{\"user\":"+ userString +"," +
                        "\"score\":100," +
                        "\"createdAt\":\"2020-03-28\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postScoreRequest).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void getAllScores() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllScoresWeekly() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/scores/weekly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void getAllScoresMonthly() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/scores/monthly")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void addNewScoreThenDelete() throws Exception{

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername5\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test5@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        Optional<User> user = userRepository.findByUsername("testusername5");
        assertTrue(user.isPresent());

        User testUser = user.get();

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String userString =  result.getResponse().getContentAsString();

        MockHttpServletRequestBuilder postScoreRequest = MockMvcRequestBuilders.post("/scores/"+testUser.getId().toString())
                .content("{\"user\":"+ userString +"," +
                        "\"score\":100," +
                        "\"createdAt\":\"2020-03-28\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postScoreRequest).andExpect(status().isOk());

        MvcResult resultScore = mockMvc.perform(MockMvcRequestBuilders.get("/scores")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        String scores = resultScore.getResponse().getContentAsString();

        String scoreId = StringUtils.substringBetween(scores, "scoreId\":", ",");

        mockMvc.perform(MockMvcRequestBuilders.delete("/scores/"+scoreId)).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void testScoreNotFoundException() throws Exception {

        Optional<Score> score = scoreRepository.findById(100L);
        if (score.isPresent()){
            mockMvc.perform(MockMvcRequestBuilders.delete("/scores/100")).andExpect(status().isOk());
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/scores/100")).andExpect(status().isNotFound());

    }

    @Test
    public void testBadRequestException() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/scores/random_string")).andExpect(status().isBadRequest());

    }

}
