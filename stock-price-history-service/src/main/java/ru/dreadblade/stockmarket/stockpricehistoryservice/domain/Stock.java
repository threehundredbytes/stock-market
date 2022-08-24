package ru.dreadblade.stockmarket.stockpricehistoryservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "currentStockPrices")
public class Stock {
    @Id
    private Long id;

    private BigDecimal price;

    @Builder.Default
    private Instant createdAt = Instant.now();
}
