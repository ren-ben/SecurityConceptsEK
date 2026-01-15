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
public class OAuth2IntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testOAuth2AuthorizationEndpointIsReachable() throws Exception {
        mockMvc.perform(get("/oauth2/authorization/google"))
                .andExpect(status().isFound());
    }

    @Test
    @WithMockUser(username = "google_user", authorities = {"OIDC_USER", "ROLE_USER"})
    public void testSocialLoginUserCanAccessUserData() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("google_user"));
    }

    @Test
    @WithMockUser(username = "google_user", authorities = {"OIDC_USER", "ROLE_USER"})
    public void testSocialLoginUserCannotAccessAdSecretArea() throws Exception {
        mockMvc.perform(get("/api/secret/students"))
                .andExpect(status().isForbidden());
    }
}
