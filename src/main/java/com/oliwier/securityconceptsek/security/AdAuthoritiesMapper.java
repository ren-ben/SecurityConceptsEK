package com.oliwier.securityconceptsek.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class AdAuthoritiesMapper implements GrantedAuthoritiesMapper {

    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Set<GrantedAuthority> mapped = new HashSet<>();

        mapped.add(new SimpleGrantedAuthority("ROLE_USER"));

        for (GrantedAuthority authority : authorities) {
            String raw = authority.getAuthority();
            String group = raw.toUpperCase();

            mapped.add(authority);

            if (group.contains("SCHUELER5BHIT")) {
                mapped.add(new SimpleGrantedAuthority("ROLE_OPS"));
            }

            if (group.contains("LEHRER")) {
                mapped.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }
        }

        return mapped;
    }
}
