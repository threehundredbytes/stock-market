package ru.dreadblade.stockmarket.accountservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockOnAccountResponseDTO {
    private Long stockId;
    private Long quantity;
    private Long reservedQuantity;
}
