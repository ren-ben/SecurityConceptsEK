package com.oliwier.securityconceptsek.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/secret")
public class SecretController {

    @GetMapping("/students")
    @PreAuthorize("hasRole('OPS')")
    public Map<String, String> getStudentSecret(Authentication auth) {
        return Map.of(
                "message", "Glückwunsch! Du bist in der 5BHIT und hast Zugriff.",
                "user", auth.getName()
        );
    }

    @GetMapping("/teachers")
    @PreAuthorize("hasRole('ADMIN')")
    public Map<String, String> getTeacherSecret() {
        return Map.of("message", "Nur für Lehrer sichtbar!");
    }
}

