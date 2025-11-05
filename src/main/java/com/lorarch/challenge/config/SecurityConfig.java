package com.lorarch.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource ds) {
        JdbcUserDetailsManager mgr = new JdbcUserDetailsManager(ds);
        mgr.setUsersByUsernameQuery(
                "SELECT USERNAME, PASSWORD, ENABLED FROM RM558024.APP_USERS WHERE USERNAME=?"
        );
        mgr.setAuthoritiesByUsernameQuery(
                "SELECT u.USERNAME, r.NAME AS AUTHORITY " +
                        "FROM RM558024.APP_USERS u " +
                        "JOIN RM558024.APP_USER_ROLES ur ON ur.USER_ID=u.ID " +
                        "JOIN RM558024.APP_ROLES r ON r.ID=ur.ROLE_ID " +
                        "WHERE u.USERNAME=?");
        return mgr;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/css/**","/js/**","/images/**",
                                "/swagger-ui/**","/v3/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/", "/motos/**", "/ocorrencias/**").authenticated()
                        .requestMatchers("/api/**").authenticated()
                        .anyRequest().authenticated()
                )
                .csrf(csrf -> csrf

                        .ignoringRequestMatchers("/api/**")
                        .csrfTokenRepository(org.springframework.security.web.csrf.CookieCsrfTokenRepository.withHttpOnlyFalse())
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/motos", true)
                        .failureUrl("/login?error")
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .deleteCookies("JSESSIONID")
                );
        return http.build();
    }
}

