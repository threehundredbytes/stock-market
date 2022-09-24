package ru.dreadblade.stockmarket.gateway.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("app.services")
@Getter
@Setter
public class StockMarketServices {
    private String accountService;
    private String orderService;
    private String paymentService;
    private String stockPriceHistoryService;
    private String stockPriceService;
    private String stockService;
    private String userService;
}
