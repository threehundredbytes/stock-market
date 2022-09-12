package ru.dreadblade.stockmarket.stockservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.dreadblade.stockmarket.stockservice.security.StockMarketJwtAuthenticationConverter;
import ru.dreadblade.stockmarket.stockservice.security.StockMarketJwtDecoder;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final StockMarketJwtDecoder decoder;
    private final StockMarketJwtAuthenticationConverter converter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .oauth2ResourceServer()
                .jwt()
                .decoder(decoder)
                .jwtAuthenticationConverter(converter);

        return httpSecurity.build();
    }
}
