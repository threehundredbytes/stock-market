package ru.dreadblade.stockmarket.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.accountservice.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
