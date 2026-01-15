package com.oliwier.securityconceptsek;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ProtectedEndpointIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testSecretStudentsEndpointForbidsNonStudents() throws Exception {
        mockMvc.perform(get("/api/secret/students"))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testProtectedEndpointRedirectsToLoginWhenNotAuthenticated() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/login"));
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_ADMIN"})
    public void testSecretTeachersEndpointAllowsTeachers() throws Exception {
        mockMvc.perform(get("/api/secret/teachers"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_OPS"})
    public void testSecretStudentsEndpointAllowsStudents() throws Exception {
        mockMvc.perform(get("/api/secret/students"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testUserDataEndpointWithAuthentication() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

}
