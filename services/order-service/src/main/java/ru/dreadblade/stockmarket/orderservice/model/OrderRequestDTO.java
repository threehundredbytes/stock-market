package ru.dreadblade.stockmarket.orderservice.model;

import lombok.*;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderRequestDTO {
    private Long stockId;
    private Long accountId;
    private BigDecimal pricePerStock;
    private Long quantity;
    private OrderType orderType;
}
