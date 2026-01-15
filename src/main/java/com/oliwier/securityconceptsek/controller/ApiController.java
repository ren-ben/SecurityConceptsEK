package com.oliwier.securityconceptsek.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ApiController {

    @GetMapping("/public/info")
    public Map<String, Object> publicInfo() {
        return Map.of(
                "status", "ok",
                "message", "This is a public endpoint - no authentication required",
                "timestamp", System.currentTimeMillis()
        );
    }

    @GetMapping("/me")
    public Map<String, Object> getCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return Map.of("authenticated", false);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("authenticated", true);
        response.put("name", auth.getName());
        response.put("authorities", auth.getAuthorities().stream()
                .map(a -> a.getAuthority())
                .collect(Collectors.toList()));

        if (auth.getPrincipal() instanceof OAuth2User) {
            OAuth2User oauth2User = (OAuth2User) auth.getPrincipal();
            response.put("email", oauth2User.getAttribute("email"));
            response.put("picture", oauth2User.getAttribute("picture"));
        }

        return response;
    }

    @GetMapping("/user/data")
    public Map<String, Object> getUserData(Authentication auth) {
        return Map.of(
                "username", auth.getName(),
                "data", "This data is visible to all authenticated users",
                "section", "user"
        );
    }


    @GetMapping("/ops/data")
    public Map<String, Object> getOpsData() {
        return Map.of(
                "data", "This data is only visible to users with OPS role",
                "section", "operations"
        );
    }

    @GetMapping("/admin/data")
    public Map<String, Object> getAdminData() {
        return Map.of(
                "data", "This data is only visible to administrators",
                "section", "admin"
        );
    }
}
