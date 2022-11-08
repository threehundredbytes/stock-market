package ru.dreadblade.stockmarket.shared.event.bus;

import ru.dreadblade.stockmarket.shared.event.IntegrationEvent;

public interface EventBus {
    void publish(String topic, IntegrationEvent event);
}
