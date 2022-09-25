package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.event.OrderConfirmedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConfirmedIntegrationEventHandler implements IntegrationEventHandler<OrderConfirmedIntegrationEvent> {
    private final OrderRepository orderRepository;

    @KafkaListener(groupId = "order-service-group", topics = "order-confirmed")
    @Override
    public void handleIntegrationEvent(OrderConfirmedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Order order = orderRepository.findById(integrationEvent.getOrderId()).orElseThrow(IllegalStateException::new);
        order.setOrderStatus(OrderStatus.CONFIRMED);

        orderRepository.save(order);
    }
}
