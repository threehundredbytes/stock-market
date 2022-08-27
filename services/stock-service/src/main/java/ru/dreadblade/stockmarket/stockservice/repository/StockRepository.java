package ru.dreadblade.stockmarket.stockservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
