package com.lnr.authentication_service.wire.security.infrastracture.primary.filter;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.infrastructure.primary.service.JwtService;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
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
import java.util.UUID;
import java.util.stream.Collectors;
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final AccountApplicationService service; // repo to check user

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String path = request.getServletPath();

        // Skip JWT check for login, registration, and other public pages


        if (request.getServletPath().startsWith("/login") ||
                request.getServletPath().startsWith("/register") ||
                request.getServletPath().startsWith("/home") ||
                request.getServletPath().startsWith("/oauth2") || // auth server
                request.getServletPath().equals("/") ||
                request.getServletPath().equals("/favicon.ico")) {
            filterChain.doFilter(request, response);
            return;
        }
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authHeader.substring(7); // Remove "Bearer "

        try {
            var claims = jwtService.extractClaims(token);

            // Extract user publicId from token
            String userPublicId = claims.getSubject();

            // ✅ Check if user exists in DB
            var userEntityOpt = service.findAccountByPublicId(new UserPublicId(UUID.fromString(userPublicId)));
            if (userEntityOpt != null) {
                SecurityContextHolder.clearContext();
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid user");
                return;
            }

            // Extract roles and authorities from claims
            @SuppressWarnings("unchecked")
            var roles = (List<String>) claims.get("roles");

            @SuppressWarnings("unchecked")
            var authorities = (List<String>) claims.get("authorities");

            var grantedAuthorities = authorities.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());

            // Add roles prefixed with "ROLE_"
            grantedAuthorities.addAll(
                    roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .toList()
            );

            var authToken = new UsernamePasswordAuthenticationToken(
                    userPublicId, null, grantedAuthorities
            );

            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception ex) {
            SecurityContextHolder.clearContext();
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
            return;
        }

        filterChain.doFilter(request, response);
    }
}
