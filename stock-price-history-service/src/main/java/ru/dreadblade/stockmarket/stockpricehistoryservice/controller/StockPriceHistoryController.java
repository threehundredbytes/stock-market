package ru.dreadblade.stockmarket.stockpricehistoryservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.stockpricehistoryservice.dto.StockPriceHistoryResponseDTO;
import ru.dreadblade.stockmarket.stockpricehistoryservice.repository.StockPriceHistoryRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/stock/")
@RequiredArgsConstructor
public class StockPriceHistoryController {
    private final StockPriceHistoryRepository stockHistoryRepository;

    @GetMapping("/{stockId}/history")
    public List<StockPriceHistoryResponseDTO> findAllByStockId(@PathVariable Long stockId) {
        return stockHistoryRepository.findAllByCompositeKeyId(stockId)
                .stream()
                .map(stockHistory -> StockPriceHistoryResponseDTO.builder()
                        .price(stockHistory.getPrice())
                        .at(stockHistory.getCompositeKey().getPriceAt())
                        .build())
                .toList();
    }
}
