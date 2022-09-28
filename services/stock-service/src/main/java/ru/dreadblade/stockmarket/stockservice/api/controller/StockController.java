package ru.dreadblade.stockmarket.stockservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.stockservice.api.model.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.api.model.StockResponseDTO;
import ru.dreadblade.stockmarket.stockservice.service.StockService;

import java.util.List;

@RestController
@RequestMapping("/stocks")
@RequiredArgsConstructor
public class StockController {

    private final StockService stockService;

    @GetMapping
    public List<StockResponseDTO> findAll() {
        return stockService.findAll();
    }

    @PostMapping
    @PreAuthorize("hasRole('business')")
    @ResponseStatus(HttpStatus.CREATED)
    public StockResponseDTO addStock(@RequestBody StockRequestDTO stockRequestDTO) {
        return stockService.addStock(stockRequestDTO);
    }
}
