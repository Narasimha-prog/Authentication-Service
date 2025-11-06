package com.lnr.authentication_service.wire.security.infrastracture.primary.config;


import com.lnr.authentication_service.auth.infrastructure.primary.service.CustomOAuth2AuthorizationService;
import com.lnr.authentication_service.wire.security.infrastracture.primary.OAuth2ClientsProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import java.util.List;
import java.util.UUID;

@Configuration
public class AuthorizationServerConfig {


    @Bean
    public RegisteredClientRepository registeredClientRepository(
            OAuth2ClientsProperties props, PasswordEncoder encoder) {

        if (props.getClients() == null || props.getClients().isEmpty()) {
            System.out.println("🚨 No OAuth2 clients loaded from YAML!");
        } else {
            props.getClients().forEach(c ->
                    System.out.println("✅ Loaded client from YAML: " + c.getClientId() + " scopes: " + c.getScopes())
            );
        }

        List<RegisteredClient> clients = props.getClients().stream()
                .map(c -> {
                    RegisteredClient.Builder builder = RegisteredClient
                            .withId(UUID.randomUUID().toString())
                            .clientId(c.getClientId())
                            .clientSecret(encoder.encode(c.getClientSecret()))
                            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC);

                    c.getGrantTypes().forEach(gt ->
                            builder.authorizationGrantType(new AuthorizationGrantType(gt))
                    );
                    c.getScopes().forEach(builder::scope);
                    builder.redirectUri(c.getRedirectUri());
                    return builder.build();
                }).toList();

        clients.forEach(c ->
                System.out.println("✅ Registered client: " + c.getClientId() + " scopes: " + c.getScopes())
        );

        return new InMemoryRegisteredClientRepository(clients);
    }


    /**
     * ✅ Basic Authorization Server Settings (issuer, etc.)
     */
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://localhost:7878").build();
    }



}
