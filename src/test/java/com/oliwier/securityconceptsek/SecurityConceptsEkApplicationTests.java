package com.oliwier.securityconceptsek;

import com.oliwier.securityconceptsek.controller.ApiController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConceptsEkApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        assertThat(applicationContext).isNotNull();
    }

    @Test
    void apiControllerBeanShouldBePresent() {
        assertThat(applicationContext.getBean(ApiController.class)).isNotNull();
    }


    @Test
    @WithMockUser(username = "testuser")
    void authenticatedUserShouldAccessMeEndpoint() throws Exception {
        mockMvc.perform(get("/api/me"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.authenticated").value(true))
                .andExpect(jsonPath("$.name").value("testuser"));
    }

    @Test
    @WithMockUser(username = "testuser", roles = {"USER"})
    void authenticatedUserShouldAccessUserDataEndpoint() throws Exception {
        mockMvc.perform(get("/api/user/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.section").value("user"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser(username = "opsuser", roles = {"OPS"})
    void opsUserShouldAccessOpsDataEndpoint() throws Exception {
        mockMvc.perform(get("/api/ops/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.section").value("operations"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    void adminUserShouldAccessAdminDataEndpoint() throws Exception {
        mockMvc.perform(get("/api/admin/data"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.section").value("admin"))
                .andExpect(jsonPath("$.data").exists());
    }

    @Test
    void unauthenticatedUserShouldNotAccessProtectedEndpoints() throws Exception {
        mockMvc.perform(get("/api/me"))
                .andExpect(status().is3xxRedirection());
    }

}
