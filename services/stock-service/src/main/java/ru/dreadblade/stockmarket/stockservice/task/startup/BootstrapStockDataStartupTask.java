package ru.dreadblade.stockmarket.stockservice.task.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.dto.StockRequestDTO;
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

        stockList.add(StockRequestDTO.builder()
                .name("Mechanical engineering INC")
                .ticker("MECH")
                .price(new BigDecimal("120"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Electronic industry INC")
                .ticker("VOLT")
                .price(new BigDecimal("110"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Alphabet INC")
                .ticker("ABC")
                .price(new BigDecimal("20"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Blockchain technologies LTD")
                .ticker("BTEC")
                .price(new BigDecimal("65"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Dairy productions INC")
                .ticker("MILK")
                .price(new BigDecimal("75"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Finance researches LTD")
                .ticker("FIRE")
                .price(new BigDecimal("50"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Agricultural Productions INC")
                .ticker("AGCU")
                .price(new BigDecimal("70"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Butcher meat productions INC")
                .ticker("MEAT")
                .price(new BigDecimal("80"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Social science INC")
                .ticker("SOSC")
                .price(new BigDecimal("35"))
                .build());

        stockList.add(StockRequestDTO.builder()
                .name("Fertilizer productions INC")
                .ticker("FERT")
                .price(new BigDecimal("95"))
                .build());

        stockList.forEach(stockService::addStock);
    }
}
