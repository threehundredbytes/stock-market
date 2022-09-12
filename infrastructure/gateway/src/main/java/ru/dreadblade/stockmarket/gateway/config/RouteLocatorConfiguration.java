package ru.dreadblade.stockmarket.gateway.config;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;

@Configuration
@RequiredArgsConstructor
public class RouteLocatorConfiguration {
    private final static String API_PATH = "/api/v1";
    private final static String COOKIE_HEADER_NAME = "Cookie";
    private final static String STOCK_PRICE_HISTORY_SERVICE_PATH = "/api/v1/stocks/{stockId}/history";
    private final static String STOCK_SERVICE_PATH = "/api/v1/stocks";
    private final static String USER_SERVICE_PATH = "/api/v1/auth/*";

    private final StockMarketServices stockMarketServices;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(stockMarketServices.getStockPriceHistoryService(), r -> r
                        .method(HttpMethod.GET)
                        .and()
                        .path(STOCK_PRICE_HISTORY_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getStockPriceHistoryService()))
                )
                .route(stockMarketServices.getStockService(), r -> r
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .and()
                        .path(STOCK_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getStockService()))
                )
                .route(stockMarketServices.getUserService(), r -> r
                        .method(HttpMethod.POST)
                        .and()
                        .path(USER_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getUserService()))
                ).build();
    }

    private String getLoadBalancerUri(String serviceName) {
        return "lb://%s".formatted(serviceName);
    }
}
