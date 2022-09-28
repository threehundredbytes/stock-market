package ru.dreadblade.stockmarket.notificationservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class NotificationResponseDTO {
    private Long id;
    private BigDecimal atPrice;
    private Long stockId;
}
