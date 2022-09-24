package ru.dreadblade.stockmarket.orderservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.orderservice.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
