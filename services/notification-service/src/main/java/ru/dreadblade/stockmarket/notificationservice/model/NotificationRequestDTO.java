package ru.dreadblade.stockmarket.notificationservice.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class NotificationRequestDTO {
    private Long stockId;
    private BigDecimal atPrice;
}
