package ru.dreadblade.stockmarket.stockservice.task.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.api.model.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;
import ru.dreadblade.stockmarket.stockservice.service.StockService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapStockDataStartupTask implements StartupTask {

    private final StockService stockService;
    private final StockRepository stockRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        run();
    }

    @Override
    public void run() {
        if (stockRepository.count() > 0) {
            return;
        }

        List<StockRequestDTO> stockList = new ArrayList<>();

        stockList.add(new StockRequestDTO("Mechanical engineering INC", "MECH", new BigDecimal("120")));
        stockList.add(new StockRequestDTO("Electronic industry INC", "VOLT", new BigDecimal("110")));
        stockList.add(new StockRequestDTO("Alphabet INC", "ABC", new BigDecimal("20")));
        stockList.add(new StockRequestDTO("Blockchain technologies LTD", "BTEC", new BigDecimal("65")));
        stockList.add(new StockRequestDTO("Dairy productions INC", "MILK", new BigDecimal("75")));

        stockList.add(new StockRequestDTO("Finance researches LTD", "FIRE", new BigDecimal("50")));
        stockList.add(new StockRequestDTO("Agricultural Productions INC", "AGCU", new BigDecimal("70")));
        stockList.add(new StockRequestDTO("Butcher meat productions INC", "MEAT", new BigDecimal("80")));
        stockList.add(new StockRequestDTO("Social science INC", "SOSC", new BigDecimal("35")));
        stockList.add(new StockRequestDTO("Fertilizer productions INC", "FERT", new BigDecimal("95")));

        stockList.forEach(stockService::addStock);
    }
}
