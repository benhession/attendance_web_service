package com.benhession.attendance_web_service.config;

import net.minidev.json.JSONArray;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;

import java.util.*;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                .csrf().disable()

                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                .authorizeRequests(auth -> auth
                        .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .antMatchers("/class/qrcode")
                            .access("hasRole('attendance_tutor') and hasAuthority('SCOPE_web_client')")
                        .antMatchers("/student/classes", "/student/attend", "/home")
                            .access("hasRole('attendance_student') and hasAuthority('SCOPE_mobile_client')")
                        .anyRequest().authenticated()
                )

                .oauth2ResourceServer().jwt()
                        .jwtAuthenticationConverter(this::getJwtAuthenticationConverter);
    }

    private AbstractAuthenticationToken getJwtAuthenticationConverter(Jwt jwt) {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();

        converter.setJwtGrantedAuthoritiesConverter(this::getGrantedAuthorities);
        converter.setPrincipalClaimName("user_name");

        return converter.convert(jwt);
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

        if (claims.containsKey("scope")) {
            String scopes = (String) claims.get("scope");
            List<String> scopeList = Arrays.asList(scopes.split(" "));

            scopeList.forEach(scope -> grantedAuthorities.add(new SimpleGrantedAuthority("SCOPE_" + scope)));

        }

        return grantedAuthorities;
    }


}
