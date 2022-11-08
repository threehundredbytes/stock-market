package ru.dreadblade.stockmarket.userservice.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfiguration {
    @Value("${app.auth-server.protocol}")
    private String protocol;

    @Value("${app.auth-server.host}")
    private String host;

    @Value("${app.auth-server.port}")
    private String port;

    @Value("${app.auth-server.realm}")
    private String realm;

    @Value("${app.auth-server.client.backend.id}")
    private String clientId;

    @Value("${app.auth-server.client.backend.secret}")
    private String clientSecret;

    @Bean
    public Keycloak keycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(protocol + "://" + host + ":" + port)
                .realm(realm)
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }
}
