package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.config.KafkaTopics;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;
import ru.dreadblade.stockmarket.orderservice.event.OrderClosedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;
import ru.dreadblade.stockmarket.orderservice.repository.StockRepository;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final OrderRepository orderRepository;
    private final StockRepository stockRepository;
    private final KafkaTopics kafkaTopics;
    private final EventBus eventBus;

    @Value("${app.order.imitate-trading}")
    private Boolean isImitatingTrading;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.stock-price-change}")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Stock stock = stockRepository.findById(integrationEvent.getStockId())
                .orElseThrow(IllegalStateException::new);

        BigDecimal currentStockPrice = stock.getPrice();
        BigDecimal newStockPrice = integrationEvent.getNewPrice();

        stock.setPrice(newStockPrice);
        stockRepository.save(stock);

        List<Order> confirmedSaleOrders = orderRepository.findConfirmedSaleOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

        if (confirmedSaleOrders.size() > 0) {
            confirmedSaleOrders.forEach(orderForSale -> {
                while (orderForSale.getCurrentQuantity() > 0) {
                    var optionalOrderForPurchase = orderRepository
                            .findConfirmedPurchaseOrderByStockAndPriceBetween(stock, currentStockPrice, newStockPrice);

                    if (optionalOrderForPurchase.isEmpty()) {
                        break;
                    }

                    var orderForPurchase = optionalOrderForPurchase.get();
                    long quantity = Math.min(orderForSale.getCurrentQuantity(), orderForPurchase.getCurrentQuantity());

                    Instant now = Instant.now();

                    orderForPurchase.setCurrentQuantity(orderForPurchase.getCurrentQuantity() + quantity);
                    orderForSale.setCurrentQuantity(orderForSale.getCurrentQuantity() - quantity);

                    if (orderForPurchase.getCurrentQuantity() == 0) {
                        closeOrder(orderForPurchase, now);
                    }

                    if (orderForSale.getCurrentQuantity() == 0) {
                        closeOrder(orderForSale, now);
                    }

                    orderRepository.save(orderForPurchase);
                    orderRepository.save(orderForSale);
                }
            });
        }

        if (isImitatingTrading) {
            orderRepository.findConfirmedSaleOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice)
                    .forEach(order -> {
                        order.setCurrentQuantity(0L);
                        closeOrder(order, Instant.now());
                        orderRepository.save(order);
                    });

            orderRepository.findConfirmedPurchaseOrdersByStockAndPriceBetween(stock, currentStockPrice, newStockPrice)
                    .forEach(order -> {
                        order.setCurrentQuantity(order.getInitialQuantity());
                        closeOrder(order, Instant.now());
                        orderRepository.save(order);
                    });
        }
    }

    private void closeOrder(Order order, Instant closedAt) {
        order.setOrderStatus(OrderStatus.CLOSED);
        order.setClosedAt(closedAt);

        var orderClosedIntegrationEvent = OrderClosedIntegrationEvent.builder()
                .accountId(order.getAccount().getId())
                .stockId(order.getStock().getId())
                .stockTicker(order.getStock().getTicker())
                .pricePerStock(order.getPricePerStock())
                .quantity(order.getInitialQuantity())
                .orderType(order.getOrderType())
                .userId(order.getAccount().getOwnerId())
                .build();

        eventBus.publish(kafkaTopics.getOrderClosed(), orderClosedIntegrationEvent);
    }
}
