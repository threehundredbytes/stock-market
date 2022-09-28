package ru.dreadblade.stockmarket.notificationservice.events;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public abstract class IntegrationEvent {
    private final UUID id;
    private final Instant createdAt;

    public IntegrationEvent() {
        this(UUID.randomUUID(), Instant.now());
    }
}