package group10.server;

import group10.server.model.Role;
import group10.server.model.User;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class RoleControllerTest extends ServerTest{

    @Test
    public void creatingRole() throws Exception{

        MockHttpServletRequestBuilder postRoleRequest = MockMvcRequestBuilders.post("/roles")
                .content("{\"roleId\":3," +
                        "\"role\":\"TRY\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRoleRequest).andExpect(status().isOk());

        Optional<Role> role = roleRepository.findById(3L);
        assertEquals(role.isPresent(), true);

    }

    @Test
    public void gettingRoles() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.get("/roles")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

    }

    @Test
    public void testBadRequestException() throws Exception{

        MockHttpServletRequestBuilder postRoleRequest = MockMvcRequestBuilders.post("/roles")
                .content("{\"roleId\":\"random_string\"," +
                        "\"role\":\"TRY\"}")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(postRoleRequest).andExpect(status().isBadRequest());

    }

}
