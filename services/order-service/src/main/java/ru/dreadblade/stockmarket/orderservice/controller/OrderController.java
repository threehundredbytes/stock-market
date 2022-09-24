package ru.dreadblade.stockmarket.orderservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.orderservice.model.OrderRequestDTO;
import ru.dreadblade.stockmarket.orderservice.model.OrderResponseDTO;
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

    @PostMapping("/orders")
    @PreAuthorize("isAuthenticated()")
    public OrderResponseDTO placeOrder(@RequestBody OrderRequestDTO requestDTO, @AuthenticationPrincipal Jwt jwt) {
        return orderService.placeOrder(requestDTO, jwt.getSubject());
    }
}