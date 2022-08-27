package ru.dreadblade.stockmarket.stockpriceservice.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class IntegrationEvent {
    private final UUID id;
    private final Instant createdAt;

    public IntegrationEvent() {
        id = UUID.randomUUID();
        createdAt = Instant.now();
    }
}
