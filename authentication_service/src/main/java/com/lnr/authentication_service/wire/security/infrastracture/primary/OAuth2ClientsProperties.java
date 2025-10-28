package com.lnr.authentication_service.wire.security.infrastracture.primary;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ConfigurationProperties(prefix = "oauth2")
@Data
public class OAuth2ClientsProperties {

        private List<ClientProperties> clients;

        @Data
        public static class ClientProperties {
            private String clientId;
            private String clientSecret;
            private String redirectUri;
            private List<String> scopes;
            private List<String> grantTypes;
        }
    }


