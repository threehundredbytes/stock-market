package ru.dreadblade.stockmarket.stockservice.domain.key;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = { "code", "createdAt" })
public class StockCompositeKey implements Serializable {
    private String code;
    private Instant createdAt;
}
