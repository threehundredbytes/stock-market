package ru.dreadblade.stockmarket.paymentservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.paymentservice.model.PaymentRequestDTO;
import ru.dreadblade.stockmarket.paymentservice.service.PaymentService;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public void createPayment(@RequestBody PaymentRequestDTO paymentRequestDTO) {
        paymentService.createPayment(paymentRequestDTO);
    }
}
