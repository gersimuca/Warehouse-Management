package com.gersimuca.Warehouse.Management.security.adapter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {

    public static final String SWAGGER_UI = "/swagger-ui/**";
    public static final String V_1_API_DOCS = "/v1/api-docs/**";
    public static final String WEBJARS = "/webjars/**";
    public static final String SWAGGER_UI_HTML = "/swagger-ui.html";
    public static final String API_DOCS = "/api-docs/**";
    public static final String ROLE_SWAGGER = "SWAGGER";
    public static final String SWAGGER_UI_INDEX_HTML = "/swagger-ui/index.html";


    @Bean
    @Order(2)
    public SecurityFilterChain swaggerUiFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher(SWAGGER_UI).
                authorizeHttpRequests(authz -> authz
                        .requestMatchers(SWAGGER_UI_HTML, SWAGGER_UI, V_1_API_DOCS, WEBJARS,
                                SWAGGER_UI_INDEX_HTML, API_DOCS)
                        .hasRole(ROLE_SWAGGER)
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }

    @Bean
    public SecurityFilterChain swaggerApiFilterChain(HttpSecurity http) throws Exception {
        return http.securityMatcher(V_1_API_DOCS).
                authorizeHttpRequests(authz -> authz
                        .requestMatchers(SWAGGER_UI_HTML, SWAGGER_UI, V_1_API_DOCS, WEBJARS,
                                SWAGGER_UI_INDEX_HTML, API_DOCS)
                        .hasRole(ROLE_SWAGGER)
                        .anyRequest().authenticated())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .build();
    }
}
