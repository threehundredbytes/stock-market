package ru.dreadblade.stockmarket.stockpriceservice.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    private Long id;

    private BigDecimal price;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
