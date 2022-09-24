package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.event.OrderClosedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.event.StockPriceChangeIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;

import java.math.BigDecimal;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class StockPriceChangeIntegrationEventHandler implements IntegrationEventHandler<StockPriceChangeIntegrationEvent> {
    private final OrderRepository orderRepository;
    private final EventBus eventBus;

    @KafkaListener(groupId = "order-service-group", topics = "stock-price-changes")
    @Override
    public void handleIntegrationEvent(StockPriceChangeIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}\n", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Long stockId = integrationEvent.getStockId();
        BigDecimal currentStockPrice = integrationEvent.getNewPrice();

        orderRepository.findConfirmedSaleOrdersByStockIdAndPriceIsLessOrEqualThan(stockId, currentStockPrice).forEach(orderForSale -> {
            while (orderForSale.getCurrentQuantity() > 0) {
                var optionalOrderForPurchase = orderRepository
                        .findConfirmedPurchaseOrderByStockIdAndPriceIsGreaterOrEqualThan(stockId, orderForSale.getPricePerStock());

                if (optionalOrderForPurchase.isEmpty()) {
                    break;
                }

                var orderForPurchase = optionalOrderForPurchase.get();
                long quantity = Math.min(orderForSale.getCurrentQuantity(), orderForPurchase.getCurrentQuantity());

                Instant now = Instant.now();

                orderForPurchase.setCurrentQuantity(orderForPurchase.getCurrentQuantity() - quantity);
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

    private void closeOrder(Order order, Instant closedAt) {
        order.setOrderStatus(OrderStatus.CLOSED);
        order.setClosedAt(closedAt);

        var orderClosedIntegrationEvent = OrderClosedIntegrationEvent.builder()
                .orderType(order.getOrderType())
                .pricePerStock(order.getPricePerStock())
                .stockId(order.getStock().getId())
                .quantity(order.getInitialQuantity())
                .accountId(order.getAccount().getId())
                .build();

        eventBus.publish("order-closed", orderClosedIntegrationEvent);
    }
}