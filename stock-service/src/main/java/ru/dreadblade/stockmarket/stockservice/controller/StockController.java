package ru.dreadblade.stockmarket.stockservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.dto.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<Stock> findAll() {
        return stockService.findAll();
    }

    @PostMapping
    public Stock addStock(@RequestBody StockRequestDTO stockRequestDTO) {
        return stockService.addStock(stockRequestDTO);
    }
}