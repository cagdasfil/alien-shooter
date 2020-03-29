package group10.server;

import group10.server.model.User;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebAppConfiguration
public class UserControllerTest extends ServerTest{

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
        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

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

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void getAllUsers() throws Exception{

        /* Run get /users method to get all users */
        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk());

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

        User testUser = user.get();

        /* Delete the user */
        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

}
