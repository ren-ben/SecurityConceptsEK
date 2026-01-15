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

        for (GrantedAuthority authority : authorities) {
            String group = authority.getAuthority().toUpperCase();

            mapped.add(authority);

            if (group.contains("WINDPARK_OPS") || group.contains("IT_OPS")) {
                mapped.add(new SimpleGrantedAuthority("ROLE_OPS"));
            }

            if (group.contains("WINDPARK_ADMIN") || group.contains("IT_ADMIN")) {
                mapped.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
            }

            mapped.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return mapped;
    }
}
