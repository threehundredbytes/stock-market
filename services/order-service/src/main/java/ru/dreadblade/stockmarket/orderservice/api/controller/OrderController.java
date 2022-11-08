package ru.dreadblade.stockmarket.orderservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderRequestDTO;
import ru.dreadblade.stockmarket.orderservice.api.model.OrderResponseDTO;
import ru.dreadblade.stockmarket.orderservice.service.OrderService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/accounts/{accountId}/orders")
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponseDTO> findAllByAccountId(@PathVariable Long accountId, @AuthenticationPrincipal Jwt jwt) {
        return orderService.findAllByAccountId(accountId, jwt.getSubject());
    }

    @GetMapping("/orders/stocks/{stockId}")
    @PreAuthorize("isAuthenticated()")
    public List<OrderResponseDTO> findAllByStockId(@PathVariable Long stockId, @AuthenticationPrincipal Jwt jwt) {
        return orderService.findAllByStockId(stockId, jwt.getSubject());
    }

    @PostMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public OrderResponseDTO placeOrder(@RequestBody OrderRequestDTO requestDTO, @AuthenticationPrincipal Jwt jwt) {
        return orderService.placeOrder(requestDTO, jwt.getSubject());
    }
}
