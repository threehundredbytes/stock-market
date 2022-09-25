package ru.dreadblade.stockmarket.paymentservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class StockMarketJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {
    private static final String ROLE_PREFIX = "ROLE_";

    @Value("${app.security.jwt.attribute.username}")
    private String usernameAttribute;

    @Value("${app.security.jwt.attribute.roles}")
    private String rolesAttribute;

    @Override
    public JwtAuthenticationToken convert(Jwt jwt) {
        return new JwtAuthenticationToken(jwt, extractRoles(jwt), jwt.getClaim(usernameAttribute));
    }

    private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
        var converter = new JwtGrantedAuthoritiesConverter();
        converter.setAuthoritiesClaimName(rolesAttribute);
        converter.setAuthorityPrefix(ROLE_PREFIX);
        return converter.convert(jwt);
    }
}
