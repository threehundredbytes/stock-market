package ru.dreadblade.stockmarket.orderservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderResponseDTO;
import ru.dreadblade.stockmarket.orderservice.domain.Order;

@Component
public class OrderMapper {
    public OrderResponseDTO mapEntityToResponseDTO(Order order) {
        return new OrderResponseDTO(order.getId(),
                order.getPricePerStock(),
                order.getCurrentQuantity(),
                order.getInitialQuantity(),
                order.getStock().getId(),
                order.getAccount().getId(),
                order.getOrderType(),
                order.getOrderStatus(),
                order.getClosedAt(),
                order.getCreatedAt());
    }
}
