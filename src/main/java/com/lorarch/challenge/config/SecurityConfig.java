package com.lorarch.challenge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource) {
        JdbcUserDetailsManager mgr = new JdbcUserDetailsManager(dataSource);
        mgr.setUsersByUsernameQuery(
                "select username, password, enabled from users where username=?"
        );
        mgr.setAuthoritiesByUsernameQuery(
                "select u.username, r.name as authority " +
                        "from users u " +
                        "join user_roles ur on ur.user_id = u.id " +
                        "join roles r on r.id = ur.role_id " +
                        "where u.username=?"
        );
        return mgr;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/css/**", "/js/**", "/images/**", "/swagger-ui/**", "/v3/api-docs/**", "/h2-console/**").permitAll()

                        // Páginas WEB (Thymeleaf)
                        .requestMatchers(HttpMethod.GET, "/", "/motos/**", "/ocorrencias/**").authenticated()

                        // API REST sob /api/**
                        .requestMatchers(HttpMethod.GET, "/api/**").authenticated()
                        .requestMatchers(HttpMethod.POST, "/api/**").hasAnyRole("ADMIN","OPERADOR")
                        .requestMatchers(HttpMethod.PUT, "/api/**").hasAnyRole("ADMIN","OPERADOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/**").hasRole("ADMIN")

                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login").permitAll()
                        .defaultSuccessUrl("/", true)
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                );

        // H2 console
        http.headers(h -> h.frameOptions(f -> f.sameOrigin()));
        http.csrf(csrf -> csrf.ignoringRequestMatchers("/h2-console/**"));

        return http.build();
    }
}
