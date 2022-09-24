package ru.dreadblade.stockmarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.orderservice.domain.Account;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
}
