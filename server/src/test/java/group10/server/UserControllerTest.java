package group10.server;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

@WebAppConfiguration
public class UserControllerTest extends ServerTest{

    @Test
    public void addUser_getUsers() throws Exception{
        MockHttpServletRequestBuilder postUserRequest = MockMvcRequestBuilders.post("/sign_up")
                .content("{\"username\":\"testusername\"," +
                        "\"password\":\"testpassword\"," +
                        "\"name\":\"testname\"," +
                        "\"surname\":\"testusername\"," +
                        "\"email\":\"test@test.com\"," +
                        "\"active\":1," +
                        "\"role\":\"USER\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postUserRequest).andDo(print()).andExpect(status().isOk());

        mockMvc.perform(MockMvcRequestBuilders.get("/users")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

}
