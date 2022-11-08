package ru.dreadblade.stockmarket.shared.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Component;

@Component
public class StockMarketJwtDecoder implements JwtDecoder {
    @Value("${app.auth-server.issuer-uri}")
    private String issuer;

    @Override
    public Jwt decode(String token) throws JwtException {
        NimbusJwtDecoder decoder = JwtDecoders.fromOidcIssuerLocation(issuer);
        decoder.setJwtValidator(delegatingValidator());
        return decoder.decode(token);
    }

    private OAuth2TokenValidator<Jwt> delegatingValidator() {
        return new DelegatingOAuth2TokenValidator<>(JwtValidators.createDefaultWithIssuer(issuer));
    }
}
