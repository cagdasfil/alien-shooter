package group10.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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
public class UserControllerTest extends ServerTest{

    @Test
    public void getAllUsers() throws Exception{

        /* Run get /users method to get all users */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users"))
                .andExpect(status().isOk()).andReturn();

        /* Get list of users as String */
        String usersString = mvcResult.getResponse().getContentAsString();

        /* Map string response to list of users */
        ObjectMapper mapper = new ObjectMapper();
        List<User> users = mapper.readValue(usersString, new TypeReference<>() {});

        /* Check whether users exist */
        assertFalse(users.isEmpty());

    }

    @Test
    public void getUserById() throws Exception{

        /* Run get /users/{id} method to get the specified user */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/2"))
                .andExpect(status().isOk()).andReturn();

        /* Get user as String from query result */
        String testUserString = mvcResult.getResponse().getContentAsString();

        /* Check whether returned user is correct */
        assertTrue(testUserString.contains("\"username\":\"user\""));

    }

    @Test
    public void getUserByUsername() throws Exception{

        /* Run get /usernames/{username} method to get the specified user */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/usernames/user"))
                .andExpect(status().isOk()).andReturn();

        /* Get user as String from query result */
        String testUserString = mvcResult.getResponse().getContentAsString();

        /* Check whether returned user is correct */
        assertTrue(testUserString.contains("\"id\":2"));

    }

    @Test
    public void addNewUserThenDelete() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername");
        assertTrue(user.isPresent());

        User testUser = user.get();

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void addNewUserThenGet() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername2\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test2@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername2");
        assertTrue(user.isPresent());

        User testUser = user.get();

        /* Get the newly created user with /users/{id} get method */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get user as String from query result */
        String testUserString = mvcResult.getResponse().getContentAsString();

        /* Create user object from retrieved json string */
        User testUserRetrieved = new Gson().fromJson(testUserString, User.class);

        /* Check whether retrieved user is correct */
        assertEquals(testUser.getId(), testUserRetrieved.getId());

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewUserThenUpdatePassword() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername3\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test3@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername3");
        assertTrue(user.isPresent());

        User testUser = user.get();

        /* Create put request to change password of the user */
        MockHttpServletRequestBuilder putUserRequest = MockMvcRequestBuilders.put("/users/"+testUser.getId().toString())
                .content("{\"username\":\"testusername3\"," +
                        "\"password\":\"changed\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test3@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(putUserRequest).andExpect(status().isOk());

        /* Run get /usernames/{username} method to get the updated user */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/usernames/testusername3"))
                .andExpect(status().isOk()).andReturn();

        /* Get user as String from query result */
        String testUserString = mvcResult.getResponse().getContentAsString();

        /* Create updated user object from json string */
        User testUserUpdated = new Gson().fromJson(testUserString, User.class);

        /* Check whether password is changed */
        assertNotEquals(testUser.getPassword(), testUserUpdated.getPassword());

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void testUserNotFoundException() throws Exception{

        /* Find whether user with id=100 exists */
        Optional<User> user = userRepository.findById(100L);

        /* If user with id=100 exists, delete it*/
        if (user.isPresent()){
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/100")).andExpect(status().isOk());
        }

        /* Get user with id=100 and expect not found error */
        mockMvc.perform(MockMvcRequestBuilders.get("/users/100")).andExpect(status().isNotFound());

    }

    @Test
    public void testBadRequestException() throws Exception{

        /* Run get /users/{id} method with a string instead of long and expect bad request*/
        mockMvc.perform(MockMvcRequestBuilders.get("/users/random_string")).andExpect(status().isBadRequest());

    }

    @Test
    public void testUserAlreadyExistsException() throws Exception{

        /* Create post request for new user */
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername6\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test6@test.com\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Find sent user in repository */
        Optional<User> user = userRepository.findByUsername("testusername6");

        /* If there is no user with name "testusername6", create it */
        if (user.isEmpty()){
            mockMvc.perform(postUserRequest).andExpect(status().isOk());
            user = userRepository.findByUsername("testusername6");
        }

        /* Send request to the server and expect conflict*/
        mockMvc.perform(postUserRequest).andExpect(status().isConflict());

        if (user.isPresent()) {
            /* Delete the user */
            User testUser = user.get();
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());
        }

    }

}
