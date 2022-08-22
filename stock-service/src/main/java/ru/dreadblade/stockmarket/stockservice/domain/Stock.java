package ru.dreadblade.stockmarket.stockservice.domain;

import lombok.*;
import ru.dreadblade.stockmarket.stockservice.domain.key.StockCompositeKey;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@IdClass(StockCompositeKey.class)
public class Stock {
    @Id
    private String code;

    @Id
    @Builder.Default
    private Instant createdAt = Instant.now();

    private String name;
    private BigDecimal price;
}
