package ru.dreadblade.stockmarket.notificationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import ru.dreadblade.stockmarket.shared.security.StockMarketJwtAuthenticationConverter;
import ru.dreadblade.stockmarket.shared.security.StockMarketJwtDecoder;

@Configuration
@EnableWebSecurity
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
