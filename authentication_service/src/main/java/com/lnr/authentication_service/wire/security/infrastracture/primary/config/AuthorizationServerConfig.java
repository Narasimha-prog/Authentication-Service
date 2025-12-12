package com.lnr.authentication_service.wire.security.infrastracture.primary.config;

import com.lnr.authentication_service.wire.security.infrastracture.primary.OAuth2ClientsProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class AuthorizationServerConfig {


    private final  OAuth2ClientsProperties props;
    private final PasswordEncoder     encoder;

    @PostConstruct
    public void debug() {

        if (props.getClients() == null || props.getClients().isEmpty()) {
            log.info("🚨 No OAuth2 clients loaded from YAML!");
        } else {
           log.info("Properties from Client Registered: {}",props.getClients().getFirst().getClientId()+" "+ Arrays.toString(props.getClients().getFirst().getScopes().toArray()));
        }
    }

    @Bean
    public RegisteredClientRepository registeredClientRepository() {

        List<RegisteredClient> clients = props.getClients().stream()
                .map(c -> {
                    RegisteredClient.Builder builder = RegisteredClient
                            .withId(UUID.randomUUID().toString())
                            .clientId(c.getClientId())
                            .clientSecret(encoder.encode(c.getClientSecret())) // IMPORTANT
                            .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                            .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                            .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                            .redirectUri(c.getRedirectUri())
                            .scope("openid")
                            .scope("profile")
                            .clientSettings(ClientSettings.builder()
                                    .requireProofKey(false)  // ❗ Very important: disable PKCE
                                    .requireAuthorizationConsent(false)
                                    .build());

                    return builder.build();
                })
                .toList();

        return new InMemoryRegisteredClientRepository(clients);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder()
                .issuer("http://localhost:7878")
                .build();
    }
}
