package ru.dreadblade.stockmarket.stockservice.task.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.dto.StockRequestDTO;
import ru.dreadblade.stockmarket.stockservice.service.StockService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapStockDataStartupTask implements StartupTask {

    private final StockService stockService;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        run();
    }

    @Override
    public void run() {
        List<StockRequestDTO> stockList = new ArrayList<>();

        stockList.add(new StockRequestDTO("MECH", "Mechanical engineering INC", new BigDecimal("120")));
        stockList.add(new StockRequestDTO("VOLT", "Electronic industry INC", new BigDecimal("110")));
        stockList.add(new StockRequestDTO("ABC", "Alphabet INC", new BigDecimal("20")));
        stockList.add(new StockRequestDTO("BTEC", "Blockchain technologies LTD", new BigDecimal("65")));
        stockList.add(new StockRequestDTO("MILK", "Dairy productions INC", new BigDecimal("75")));
        stockList.add(new StockRequestDTO("FIRE", "Finance research LT", new BigDecimal("50")));
        stockList.add(new StockRequestDTO("AGCU", "Agricultural Productions INC", new BigDecimal("70")));
        stockList.add(new StockRequestDTO("MEAT", "Butcher meat productions INC", new BigDecimal("80")));
        stockList.add(new StockRequestDTO("SOSC", "Social science INC", new BigDecimal("35")));
        stockList.add(new StockRequestDTO("FERT", "Fertilizer productions INC", new BigDecimal("95")));

        stockList.forEach(stockService::addStock);
    }
}
