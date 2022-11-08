package ru.dreadblade.stockmarket.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.paymentservice.domain.Account;
import ru.dreadblade.stockmarket.paymentservice.domain.Payment;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    List<Payment> findAllByAccount(Account account);
}
