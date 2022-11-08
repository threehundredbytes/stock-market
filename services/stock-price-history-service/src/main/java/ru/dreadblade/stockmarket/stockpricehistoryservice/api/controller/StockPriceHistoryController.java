package ru.dreadblade.stockmarket.stockpricehistoryservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.stockpricehistoryservice.api.model.StockPriceHistoryResponseDTO;
import ru.dreadblade.stockmarket.stockpricehistoryservice.service.StockPriceHistoryService;

import java.util.List;

@RestController
@RequestMapping("/stocks/")
@RequiredArgsConstructor
public class StockPriceHistoryController {
    private final StockPriceHistoryService stockPriceHistoryService;

    @GetMapping("/{stockId}/history")
    public List<StockPriceHistoryResponseDTO> findAllByStockId(@PathVariable Long stockId) {
        return stockPriceHistoryService.findAllByStockId(stockId);
    }
}
