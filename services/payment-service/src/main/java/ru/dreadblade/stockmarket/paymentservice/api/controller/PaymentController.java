package ru.dreadblade.stockmarket.paymentservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.paymentservice.api.model.PaymentRequestDTO;
import ru.dreadblade.stockmarket.paymentservice.api.model.PaymentResponseDTO;
import ru.dreadblade.stockmarket.paymentservice.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @GetMapping("/accounts/{accountId}/payments")
    public List<PaymentResponseDTO> findAllByAccountId(@PathVariable Long accountId, @AuthenticationPrincipal Jwt jwt) {
        return paymentService.findAllByAccountId(accountId, jwt.getSubject());
    }

    @PostMapping("/payments")
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponseDTO createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO, @AuthenticationPrincipal Jwt jwt) {
        return paymentService.createPayment(paymentRequestDTO, jwt.getSubject());
    }
}
