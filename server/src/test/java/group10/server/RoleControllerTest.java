package group10.server;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import group10.server.model.Role;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebAppConfiguration
public class RoleControllerTest extends ServerTest{

    @Test
    public void creatingRole() throws Exception{

        /* Create post request for new role */
        MockHttpServletRequestBuilder postRoleRequest = MockMvcRequestBuilders.post("/roles")
                .content("{\"roleId\":3," +
                        "\"role\":\"TRY\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server */
        mockMvc.perform(postRoleRequest).andExpect(status().isOk());

        /* Run get /roles method to get all roles */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of roles as String */
        String rolesString = mvcResult.getResponse().getContentAsString();

        /* Check whether role is created */
        assertTrue(rolesString.contains("\"role\":\"TRY\""));

    }

    @Test
    public void gettingRoles() throws Exception{

        /* Run get /roles method to get all roles */
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/roles")).andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andReturn();

        /* Get list of roles as String */
        String rolesString = mvcResult.getResponse().getContentAsString();

        /* Map string response to list of roles */
        ObjectMapper mapper = new ObjectMapper();
        List<Role> roles = mapper.readValue(rolesString, new TypeReference<>() {});

        /* Check whether roles exist */
        assertFalse(roles.isEmpty());

    }

    @Test
    public void testBadRequestException() throws Exception{

        /* Create erroneous post request for new role */
        MockHttpServletRequestBuilder postRoleRequest = MockMvcRequestBuilders.post("/roles")
                .content("{\"roleId\":\"random_string\"," +
                        "\"role\":\"TRY\"}")
                .contentType(MediaType.APPLICATION_JSON);

        /* Send request to the server and expect bad request */
        mockMvc.perform(postRoleRequest).andExpect(status().isBadRequest());

    }

}
