package ru.dreadblade.stockmarket.notificationservice.events.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.events.OrderConfirmedIntegrationEvent;
import ru.dreadblade.stockmarket.shared.event.handler.IntegrationEventHandler;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderConfirmedIntegrationEventHandler implements IntegrationEventHandler<OrderConfirmedIntegrationEvent> {
    private final SimpMessagingTemplate messagingTemplate;

    @KafkaListener(groupId = "${app.kafka.consumer.group}", topics = "${app.kafka.topic.order-confirmed}")
    @Override
    public void handleIntegrationEvent(OrderConfirmedIntegrationEvent integrationEvent) {
        log.trace("Handling integration event: {} ({}): {}", integrationEvent.getId().toString(),
                integrationEvent.getClass().getSimpleName(), integrationEvent.getCreatedAt().toString());

        messagingTemplate.convertAndSendToUser(integrationEvent.getUserId(), "/topic/notifications/orders/confirmed", integrationEvent);
    }
}
