package ru.dreadblade.stockmarket.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@Configuration
public class GatewaySecurityConfiguration {
    @Value("${app.security.jwt.attribute.username}")
    private String usernameAttributeName;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        httpSecurity
                .csrf().disable()
                .authorizeExchange()
                .anyExchange().permitAll()
                .and()
                .oauth2ResourceServer()
                .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwt -> {
                    var token = new JwtAuthenticationConverter().convert(jwt);
                    return Mono.just(new JwtAuthenticationToken(jwt, token.getAuthorities(), usernameAttributeName));
                }));

        return httpSecurity.build();
    }
}
