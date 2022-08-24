package ru.dreadblade.stockmarket.stockpricehistoryservice.domain.key;

import lombok.*;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPriceHistoryCompositeKey implements Serializable {
    private Long id;
    private Instant priceAt;
}
