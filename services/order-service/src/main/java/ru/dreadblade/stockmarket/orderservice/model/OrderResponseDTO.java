package ru.dreadblade.stockmarket.orderservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.dreadblade.stockmarket.orderservice.domain.Order;
import ru.dreadblade.stockmarket.orderservice.domain.OrderStatus;
import ru.dreadblade.stockmarket.orderservice.domain.OrderType;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
public class OrderResponseDTO {
    private Long id;
    private BigDecimal pricePerStock;
    private Long currentQuantity;
    private Long initialQuantity;
    private Long stockId;
    private Long accountId;
    private OrderType orderType;
    private OrderStatus orderStatus;
    private Instant closedAt;
    private Instant createdAt;

    public static OrderResponseDTO map(Order order) {
        return OrderResponseDTO.builder()
                .id(order.getId())
                .pricePerStock(order.getPricePerStock())
                .currentQuantity(order.getCurrentQuantity())
                .initialQuantity(order.getInitialQuantity())
                .stockId(order.getStock().getId())
                .accountId(order.getAccount().getId())
                .orderType(order.getOrderType())
                .orderStatus(order.getOrderStatus())
                .closedAt(order.getClosedAt())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
