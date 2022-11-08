package ru.dreadblade.stockmarket.accountservice.event.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;

@Component
@RequiredArgsConstructor
public class KafkaEventBus implements EventBus {
    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

    @Override
    public void publish(String topic, IntegrationEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
