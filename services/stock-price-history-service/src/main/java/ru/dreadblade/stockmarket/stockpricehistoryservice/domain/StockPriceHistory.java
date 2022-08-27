package ru.dreadblade.stockmarket.stockpricehistoryservice.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import ru.dreadblade.stockmarket.stockpricehistoryservice.domain.key.StockPriceHistoryCompositeKey;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "stockPriceHistory")
public class StockPriceHistory {
    @Id
    private StockPriceHistoryCompositeKey compositeKey;

    private BigDecimal price;
}
