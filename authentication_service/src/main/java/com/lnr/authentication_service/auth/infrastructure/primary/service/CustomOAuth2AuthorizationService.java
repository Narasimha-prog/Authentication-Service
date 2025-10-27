package com.lnr.authentication_service.auth.infrastructure.primary.service;

import com.lnr.authentication_service.auth.application.AccountApplicationService;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccount;
import com.lnr.authentication_service.auth.domain.account.aggrigate.UserAccountBuilder;
import com.lnr.authentication_service.auth.domain.account.vo.RefreshPublicId;
import com.lnr.authentication_service.shared.domain.user.vo.UserPublicId;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Custom implementation of OAuth2AuthorizationService that delegates
 * refresh token persistence and revocation to the domain-level RefreshTokenService.
 */
@Service
@RequiredArgsConstructor
@Primary
public class CustomOAuth2AuthorizationService implements OAuth2AuthorizationService {

    private final RefreshTokenService refreshTokenService;

    private final AccountApplicationService applicationService;
    private final RegisteredClientRepository registeredClientRepository;

    /**
     * Called when a new OAuth2Authorization (e.g., login or token refresh) is created.
     * We delegate to our domain service to persist a refresh token.
     */
    @Override
    public void save(OAuth2Authorization authorization) {
        if (authorization == null || authorization.getRefreshToken() == null) {
            return;
        }

        OAuth2RefreshToken refreshToken = authorization.getRefreshToken().getToken();

        // Extract the principal/user ID from the authorization
        String principalName = authorization.getPrincipalName();
        if (principalName == null || principalName.isBlank()) return;

        try {
            UUID userId = UUID.fromString(principalName);
            UserPublicId userPublicId = new UserPublicId(userId);




            refreshTokenService.saveTokens(applicationService.findAccountByPublicId(userPublicId));

        } catch (IllegalArgumentException ignored) {
            // principalName isn't a UUID (e.g., username-based principal)
        }
    }

    /**
     * Called when an authorization and its tokens are revoked or deleted.
     */
    @Override
    public void remove(OAuth2Authorization authorization) {
        if (authorization == null) return;

            UUID tokenId = UUID.fromString(authorization.getId());

            refreshTokenService.revokeToken(new RefreshPublicId(tokenId));

    }

    /**
     * Optional lookup by authorization ID.
     */
    @Override
    public OAuth2Authorization findById(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            Optional<?> token = refreshTokenService.findByPublicId(new UserPublicId(uuid));

            // ✅ Get RegisteredClient properly from repository
            RegisteredClient registeredClient = registeredClientRepository.findByClientId("oidc-client");
            if (registeredClient == null) return null;

            return token.map(t -> OAuth2Authorization.withRegisteredClient(registeredClient)
                    .principalName(uuid.toString())
                    .attribute("refresh_token", t)
                    .build()).orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    /**
     * Optional lookup by token string.
     */
    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {

        return refreshTokenService.findByToken(token)
                .map(rt -> {
                    // Lookup client (depends on your app logic)
                    RegisteredClient client = registeredClientRepository.findByClientId("your-client-id");

                    OAuth2Authorization.Builder builder = OAuth2Authorization.withRegisteredClient(client)
                            .principalName(rt.getUserId().value().toString())
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN);

                    // Add refresh token
                    builder.token(new OAuth2RefreshToken(
                            rt.getToken(),
                            rt.getExpiryDate().minusSeconds(3600),
                            rt.getExpiryDate()
                    ));

                    return builder.build();
                })
                .orElse(null);
    }
}
