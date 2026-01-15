package com.oliwier.securityconceptsek.config;

import com.oliwier.securityconceptsek.security.AdAuthoritiesMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@Profile("ad")
public class SecurityAdConfig {

    @Bean
    public ActiveDirectoryLdapAuthenticationProvider adProvider(
            @Value("${ad.domain}") String domain,
            @Value("${ad.url}") String url,
            AdAuthoritiesMapper mapper) {

        String rootDn = "DC=tgm,DC=ac,DC=at";

        ActiveDirectoryLdapAuthenticationProvider provider =
                new ActiveDirectoryLdapAuthenticationProvider(domain, url, rootDn);

        provider.setSearchFilter("(&(objectClass=user)(|(userPrincipalName={0})(sAMAccountName={0})))");

        provider.setAuthoritiesMapper(mapper);
        provider.setConvertSubErrorCodesToExceptions(true);
        provider.setUseAuthenticationRequestCredentials(true);

        return provider;
    }




    @Bean
    public AuthenticationManager authManager(ActiveDirectoryLdapAuthenticationProvider adProvider) {
        return new ProviderManager(adProvider);
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            AuthenticationManager authManager,
            @Qualifier("corsConfigurationSource") CorsConfigurationSource corsSource) throws Exception {

        http
                .cors(c -> c.configurationSource(corsSource))
                .csrf(c -> c.disable())
                .authenticationManager(authManager)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/public/**").permitAll()
                        .requestMatchers("/api/ops/**").hasRole("OPS")
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll()
                )
                // WICHTIG: Form Login für Vue optimiert (JSON statt Redirect)
                .formLogin(form -> form
                        .loginProcessingUrl("/api/login")
                        .successHandler((req, res, auth) -> {
                            res.setStatus(200);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"authenticated\":true}");
                        })
                        .failureHandler((req, res, ex) -> {
                            res.setStatus(401);
                            res.setContentType("application/json");
                            res.getWriter().write("{\"error\":\"Login failed\"}");
                        })
                        .permitAll()
                )
                .logout(l -> l
                        .logoutUrl("/api/logout")
                        .logoutSuccessHandler((req, res, auth) -> res.setStatus(200))
                );

        return http.build();
    }


}
