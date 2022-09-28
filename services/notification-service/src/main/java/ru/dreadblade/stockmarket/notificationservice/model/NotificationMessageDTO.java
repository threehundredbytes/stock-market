package ru.dreadblade.stockmarket.notificationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class NotificationMessageDTO {
    private String stockTicker;
    private BigDecimal pricePerStock;
}
