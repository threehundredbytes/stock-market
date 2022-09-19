package ru.dreadblade.stockmarket.accountservice.event.handler;

public interface IntegrationEventHandler<T> {
    void handleIntegrationEvent(T integrationEvent);
}
