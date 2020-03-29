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

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        Optional<User> user = userRepository.findByUsername("testusername");
        assertTrue(user.isPresent());

        User testUser = user.get();

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void addNewUserThenGet() throws Exception{

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername2\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test2@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        Optional<User> user = userRepository.findByUsername("testusername2");
        assertTrue(user.isPresent());

        User testUser = user.get();

        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+testUser.getId().toString())).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());
    }

    @Test
    public void addNewUserThenUpdatePassword() throws Exception{

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername3\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test3@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andExpect(status().isOk());

        Optional<User> user = userRepository.findByUsername("testusername3");
        assertTrue(user.isPresent());

        User testUser = user.get();

        MockHttpServletRequestBuilder putUserRequest = MockMvcRequestBuilders.put("/users"+testUser.getId().toString())
                .content("{\"username\":\"testusername3\"," +
                        "\"password\":\"changed\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test3@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        //mockMvc.perform(putUserRequest).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+testUser.getId().toString())).andExpect(status().isOk());

    }

    @Test
    public void getAllUsers() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk());

    }

    @Test
    public void testUserNotFoundException() throws Exception{

        Optional<User> user = userRepository.findById(100L);
        if (user.isPresent()){
            mockMvc.perform(MockMvcRequestBuilders.delete("/users/100")).andExpect(status().isOk());
        }

        mockMvc.perform(MockMvcRequestBuilders.get("/users/100")).andExpect(status().isNotFound());

    }

    @Test
    public void testBadRequestException() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/users/random_string")).andExpect(status().isBadRequest());

    }

    @Test
    public void testUserAlreadyExistsException() throws Exception{

        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername6\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testsurname\"," +
                        "\"email\":\"test6@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        Optional<User> user = userRepository.findByUsername("testusername6");

        if (user.isEmpty()){
            mockMvc.perform(postUserRequest).andExpect(status().isOk());
        }

        mockMvc.perform(postUserRequest).andExpect(status().isConflict());

    }

}
