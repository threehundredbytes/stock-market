package ru.dreadblade.stockmarket.paymentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.paymentservice.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
