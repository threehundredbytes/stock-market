package ru.dreadblade.stockmarket.orderservice.event.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.event.OrderRejectedIntegrationEvent;
import ru.dreadblade.stockmarket.orderservice.repository.OrderRepository;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRejectedIntegrationEventHandler implements IntegrationEventHandler<OrderRejectedIntegrationEvent> {
    private final OrderRepository orderRepository;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.order-rejected}")
    @Override
    public void handleIntegrationEvent(OrderRejectedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        Order order = orderRepository.findById(integrationEvent.getOrderId()).orElseThrow(IllegalStateException::new);
        order.setOrderStatus(OrderStatus.REJECTED);
        order.setClosedAt(Instant.now());

        orderRepository.save(order);
    }
}
