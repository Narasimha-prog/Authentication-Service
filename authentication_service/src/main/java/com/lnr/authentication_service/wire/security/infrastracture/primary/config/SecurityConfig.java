package com.lnr.authentication_service.wire.security.infrastracture.primary.config;

import com.lnr.authentication_service.wire.security.infrastracture.primary.filter.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
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

  private static final String[] PUBLIC_KEY_WHITELIST={"/.well-known/**","/auth/api/register","/register"};



    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http) throws Exception {
        // Create a local instance — do NOT call .getEndpointsMatcher() yet
        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
                OAuth2AuthorizationServerConfigurer.authorizationServer();

        // ✅ Attach it FIRST — this initializes its internal matchers
        http.with(authorizationServerConfigurer, (authorizationServer) ->
                authorizationServer.oidc(Customizer.withDefaults())
        );

        // ✅ Now it’s safe to use getEndpointsMatcher()
        http
                .securityMatcher(authorizationServerConfigurer.getEndpointsMatcher())
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .csrf(csrf -> csrf.ignoringRequestMatchers(authorizationServerConfigurer.getEndpointsMatcher()))
                .exceptionHandling(exceptions -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                );


        return http.build();
    }



    @Bean
  @Order(2)
  public SecurityFilterChain configure(HttpSecurity http) throws Exception {

    http
      .csrf(AbstractHttpConfigurer::disable)
      .authorizeHttpRequests(authorize -> authorize
        // Public GET endpoints
        .requestMatchers(HttpMethod.GET,
          "/api/categories",
          "/api/products-shop/**",
          "/api/orders/get-cart-details/**"
        ).permitAll()

        // Public POST endpoints (webhooks)
        .requestMatchers(
          "/api/orders/webhook/**"
        ).permitAll()

              .requestMatchers(HttpMethod.GET,PUBLIC_KEY_WHITELIST).permitAll()
        // Swagger UI / OpenAPI endpoints
        .requestMatchers(SWAGGER_WHITELIST).permitAll()

        // Actuator endpoints
        .requestMatchers(ACTUATOR_WHITELIST).permitAll()


        // All other /api/** endpoints require authentication
        .requestMatchers("/api/**").authenticated()

        // Any other requests are permitted
        .anyRequest().permitAll()

      )
      // Add JWT filter before UsernamePasswordAuthenticationFilter
      .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)

            // ✅ Add form login here
            .formLogin(form -> form
                    .loginPage("/login")                // GET /login shows your Thymeleaf page
                    .loginProcessingUrl("/login")       // POST /login handled by Spring Security
                    .failureUrl("/login?error=true")
                    // After login, resume OAuth2 flow instead of redirecting to /
                    .successHandler((request, response, authentication) -> {
                        response.sendRedirect("/oauth2/authorize?" + request.getQueryString());
                    })
                    .permitAll()
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return http.build();
  }
}

