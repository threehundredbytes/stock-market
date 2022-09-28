package ru.dreadblade.stockmarket.notificationservice.events.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
