package com.oliwier.securityconceptsek.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.ldap.authentication.ad.ActiveDirectoryLdapAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
@Profile("ad")
public class AdStartupCheck implements ApplicationRunner {

    private final ActiveDirectoryLdapAuthenticationProvider adProvider;

    @Value("${AD_CHECK_USER:}")
    private String adUser;

    @Value("${AD_CHECK_PASSWORD:}")
    private String adPassword;

    public AdStartupCheck(ActiveDirectoryLdapAuthenticationProvider adProvider) {
        this.adProvider = adProvider;
    }

    @Override
    public void run(ApplicationArguments args) {
        if (adUser == null || adUser.isBlank() || adPassword == null || adPassword.isBlank()) {
            System.out.println("AD Startup Check übersprungen (AD_CHECK_USER/PASSWORD fehlen)");
            return;
        }

        System.out.println("Prüfe AD-Verbindung mit User: " + adUser + " ...");

        try {
            adProvider.authenticate(
                    new UsernamePasswordAuthenticationToken(adUser, adPassword)
            );

            System.out.println("AD Verbindung ERFOLGREICH! Login funktioniert.");

        } catch (Exception e) {
            System.err.println("\nAD VERBINDUNG FEHLGESCHLAGEN!");
            System.err.println("Fehler: " + e.getMessage());
        }
    }
}
