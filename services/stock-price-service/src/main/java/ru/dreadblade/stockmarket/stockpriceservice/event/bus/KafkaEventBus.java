package ru.dreadblade.stockmarket.stockpriceservice.event.bus;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockpriceservice.event.IntegrationEvent;

@Component
@RequiredArgsConstructor
public class KafkaEventBus implements EventBus {
    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

    @Override
    public void publish(String topic, IntegrationEvent event) {
        kafkaTemplate.send(topic, event);
    }
}
