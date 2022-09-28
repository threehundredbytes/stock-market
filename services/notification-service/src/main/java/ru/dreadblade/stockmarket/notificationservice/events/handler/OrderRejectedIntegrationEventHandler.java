package ru.dreadblade.stockmarket.notificationservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.events.OrderRejectedIntegrationEvent;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderRejectedIntegrationEventHandler implements IntegrationEventHandler<OrderRejectedIntegrationEvent> {
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.order-rejected}")
    @Override
    public void handleIntegrationEvent(OrderRejectedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        messagingTemplate.convertAndSendToUser(integrationEvent.getUserId(), "/topic/orders/rejected", integrationEvent);
    }
}
