package com.lnr.authentication_service.auth.infrastructure.primary;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "
        try {
            var claims = jwtService.extractClaims(token);

            var userId = claims.getSubject(); // sub
            @SuppressWarnings("unchecked")
            var roles = (List<String>) claims.get("roles"); // roles claim
            @SuppressWarnings("unchecked")
            var authorities = (List<String>) claims.get("authorities"); // authorities claim

            var grantedAuthorities = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Add roles as prefixed authorities (optional)
            grantedAuthorities.addAll(
                    roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList())
            );

            var authToken = new UsernamePasswordAuthenticationToken(
                    userId, null, grantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception ex) {
            SecurityContextHolder.clearContext(); // invalid token
        }

        filterChain.doFilter(request, response);
    }
}
