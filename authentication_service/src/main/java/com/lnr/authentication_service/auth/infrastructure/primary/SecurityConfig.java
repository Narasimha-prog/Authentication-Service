package com.lnr.authentication_service.auth.infrastructure.primary;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private static final String[] SWAGGER_WHITELIST = {
    "/swagger-ui.html",
    "/swagger-ui/**",
    "/v3/api-docs/**",
    "/v3/api-docs.yaml",
    "/v3/api-docs"
  };
  private static final String[] ACTUATOR_WHITELIST = {
    "/actuator/health",
    "/actuator/info"
  };
  @Bean
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    return http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        // Public GET endpoints
        .requestMatchers(HttpMethod.GET,
          "/api/categories",
          "/api/products-shop/**",
          "/api/orders/get-cart-details/**"
        ).permitAll()

        // Public POST endpoints (webhooks)
        .requestMatchers(HttpMethod.POST,
          "/api/orders/webhook/**"
        ).permitAll()

        // Swagger UI / OpenAPI endpoints
        .requestMatchers(SWAGGER_WHITELIST).permitAll()

        // Actuator endpoints
        .requestMatchers(ACTUATOR_WHITELIST).permitAll()


        // All other /api/** endpoints require authentication
        .requestMatchers("/api/**").authenticated()

        // Any other requests are permitted
        .anyRequest().permitAll()

      )
      .build();
  }
}

