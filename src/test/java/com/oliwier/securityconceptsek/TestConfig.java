package com.oliwier.securityconceptsek;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;

@TestConfiguration
public class TestConfig {

    @Bean
    @Primary
    public ActiveDirectoryLdapAuthenticationProvider testLdapProvider() {
        return new ActiveDirectoryLdapAuthenticationProvider("test.domain", "ldap://test:389");
    }
}
