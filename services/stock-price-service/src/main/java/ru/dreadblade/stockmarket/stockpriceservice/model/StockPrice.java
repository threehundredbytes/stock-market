package ru.dreadblade.stockmarket.stockpriceservice.model;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockPrice implements Serializable {
    private Long stockId;

    private BigDecimal price;
}
