package ru.dreadblade.stockmarket.stockservice.event.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
