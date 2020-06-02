package group10.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import group10.server.model.Match;
import group10.server.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class MatchControllerTest extends ServerTest{

    @Test
    public void getAllMatches() throws Exception{

        /* Create post request for new match */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/add_match")
                .content("{\"serverUsername\":\"testServer\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient\"," +
                        "\"clientStatus\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent match in repository */
        Optional<Match> match = matchRepository.findByServerUsername("testServer");
        assertTrue(match.isPresent());

        /* Get retrieved match object */
        Match testMatch = match.get();

        /* Run get /matches method to get all matches */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/matches"))
                .andExpect(status().isOk()).andReturn();

        /* Get list of matches as String */
        String matchesString = mvcResult.getResponse().getContentAsString();

        /* Map string response to list of matches */
        ObjectMapper mapper = new ObjectMapper();
        List<Match> matches = mapper.readValue(matchesString, new TypeReference<>() {});

        /* Check whether users exist */
        assertFalse(matches.isEmpty());

        /* Delete the match */
        mockMvc.perform(MockMvcRequestBuilders.delete("/matches/"+testMatch.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewMatch() throws Exception{

        /* Create post request for new match */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/add_match")
                .content("{\"serverUsername\":\"testServer2\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient2\"," +
                        "\"clientStatus\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent match in repository */
        Optional<Match> match = matchRepository.findByServerUsername("testServer2");
        assertTrue(match.isPresent());

        /* Get retrieved match object */
        Match testMatch = match.get();

        /* Delete the match */
        mockMvc.perform(MockMvcRequestBuilders.delete("/matches/"+testMatch.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewMatchThenGetMatches() throws Exception{

        /* Create post request for new match */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/add_match")
                .content("{\"serverUsername\":\"testServer3\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient3\"," +
                        "\"clientStatus\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent match in repository */
        Optional<Match> match = matchRepository.findByServerUsername("testServer3");
        assertTrue(match.isPresent());

        /* Get retrieved match object */
        Match testMatch = match.get();

        /* Run get /matches method to get all matches */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/matches"))
                .andExpect(status().isOk()).andReturn();

        /* Get list of matches as String */
        String matchesString = mvcResult.getResponse().getContentAsString();

        /* Map string response to list of matches */
        ObjectMapper mapper = new ObjectMapper();
        List<Match> matches = mapper.readValue(matchesString, new TypeReference<>() {});

        /* Check whether users exist */
        assertFalse(matches.isEmpty());

        /* Delete the match */
        mockMvc.perform(MockMvcRequestBuilders.delete("/matches/"+testMatch.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewMatchThenDelete() throws Exception{

        /* Create post request for new match */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/add_match")
                .content("{\"serverUsername\":\"testServer4\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient4\"," +
                        "\"clientStatus\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent match in repository */
        Optional<Match> match = matchRepository.findByServerUsername("testServer4");
        assertTrue(match.isPresent());

        /* Get retrieved match object */
        Match testMatch = match.get();

        /* Delete the match */
        mockMvc.perform(MockMvcRequestBuilders.delete("/matches/"+testMatch.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewMatchThenUpdateStatus() throws Exception{

        /* Create post request for new match */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/add_match")
                .content("{\"serverUsername\":\"testServer5\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient5\"," +
                        "\"clientStatus\":\"test\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent match in repository */
        Optional<Match> match = matchRepository.findByServerUsername("testServer5");
        assertTrue(match.isPresent());

        /* Get retrieved match object */
        Match testMatch = match.get();

        /* Create put request to change clientStatus of the match */
        MockHttpServletRequestBuilder putMatchRequest = MockMvcRequestBuilders.put("/matches/"+testMatch.getId().toString())
                .content("{\"serverUsername\":\"testServer5\"," +
                        "\"serverIP\":\"0.0.0.0\"," +
                        "\"serverPort\":\"0\"," +
                        "\"serverStatus\":\"test\"," +
                        "\"clientUsername\":\"testClient5\"," +
                        "\"clientStatus\":\"ready\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(putMatchRequest).andExpect(status().isOk());

        /* Find updated match in repository */
        Optional<Match> updatedMatch = matchRepository.findByServerUsername(testMatch.getServerUsername());
        assertTrue(updatedMatch.isPresent());

        /* Get updated match object */
        Match testUpdatedMatch = updatedMatch.get();

        /* Check the clientStatus */
        assertEquals(testUpdatedMatch.getClientStatus(), "ready");

        /* Delete the match */
        mockMvc.perform(MockMvcRequestBuilders.delete("/matches/"+testMatch.getId().toString())).andExpect(status().isOk());
    }

}
