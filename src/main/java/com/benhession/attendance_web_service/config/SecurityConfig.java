package com.benhession.attendance_web_service.config;


import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .authorizeRequests(auth -> auth
                        .antMatchers("/home", "home/**").hasRole("attendance_student")
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer().jwt()
                        .jwtAuthenticationConverter(getJwtAuthenticationConverter());
    }

    private Converter<Jwt, ? extends AbstractAuthenticationToken> getJwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(this::getGrantedAuthorities);

        return converter;
    }

    private Set<GrantedAuthority> getGrantedAuthorities(Jwt jwt) {
        Map<String, Object> claims = jwt.getClaims();
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        if (claims.containsKey("authorities")) {
            JSONArray authJSONArr = (JSONArray) claims.get("authorities");

            authJSONArr.forEach(auth -> grantedAuthorities.add(
                    new SimpleGrantedAuthority("ROLE_" + auth.toString()))
            );
        }

        return grantedAuthorities;
    }
}
