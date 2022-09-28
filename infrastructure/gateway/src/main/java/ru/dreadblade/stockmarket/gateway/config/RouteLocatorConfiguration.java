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

    private final static String[] ACCOUNT_SERVICE_PATH = { "/api/v1/accounts", "/api/v1/accounts/{accountId}/stocks" };
    private final static String[] NOTIFICATION_SERVICE_PATH = { "/api/v1/notifications", "/api/v1/notifications/{notificationId}", "/api/v1/notifications/websocket/connect" };
    private final static String[] ORDER_SERVICE_PATH = { "/api/v1/orders", "/api/v1/accounts/{accountId}/orders"};
    private final static String[] PAYMENT_SERVICE_PATH = { "/api/v1/payments", "/api/v1/accounts/{accountId}/payments" };

    private final static String[] STOCK_PRICE_HISTORY_SERVICE_PATH = { "/api/v1/stocks/{stockId}/history" };
    private final static String[] STOCK_SERVICE_PATH = { "/api/v1/stocks", "/api/v1/stocks/websocket/connect" };
    private final static String[] USER_SERVICE_PATH = { "/api/v1/auth/signup" };

    private final StockMarketServices stockMarketServices;

    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(stockMarketServices.getAccountService(), r -> r
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .and()
                        .path(ACCOUNT_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getAccountService()))
                )
                .route(stockMarketServices.getNotificationService(), r -> r
                        .method(HttpMethod.GET, HttpMethod.POST, HttpMethod.DELETE)
                        .and()
                        .path(NOTIFICATION_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getNotificationService()))
                )
                .route(stockMarketServices.getOrderService(), r -> r
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .and()
                        .path(ORDER_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getOrderService()))
                )
                .route(stockMarketServices.getPaymentService(), r -> r
                        .method(HttpMethod.GET, HttpMethod.POST)
                        .and()
                        .path(PAYMENT_SERVICE_PATH)
                        .filters(f -> f
                                .removeRequestHeader(COOKIE_HEADER_NAME)
                                .rewritePath(API_PATH, "")
                        )
                        .uri(getLoadBalancerUri(stockMarketServices.getPaymentService()))
                )
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
