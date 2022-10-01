package ru.dreadblade.stockmarket.notificationservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.events.OrderClosedIntegrationEvent;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderClosedIntegrationEventHandler implements IntegrationEventHandler<OrderClosedIntegrationEvent> {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.order-closed}")
    @Override
    public void handleIntegrationEvent(OrderClosedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        simpMessagingTemplate.convertAndSendToUser(integrationEvent.getUserId(), "/topic/orders/closed", integrationEvent);
    }
}
