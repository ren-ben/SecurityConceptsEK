package com.oliwier.securityconceptsek;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "testuser", authorities = {"ROLE_USER"})
    public void testCsrfIsDisabled() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isOk());
    }


    @Test
    public void testCorsIsEnabled() throws Exception {
        mockMvc.perform(options("/api/user/data")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "GET"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "testuser")
    public void testLogoutFunctionality() throws Exception {
        mockMvc.perform(post("/api/logout"))
                .andExpect(status().isOk());
    }
}
