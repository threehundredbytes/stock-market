package ru.dreadblade.stockmarket.stockservice.task.startup;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.stockservice.domain.Stock;
import ru.dreadblade.stockmarket.stockservice.repository.StockRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class BootstrapStockDataStartupTask implements StartupTask {

    private final StockRepository stockRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        run();
    }

    @Override
    public void run() {
        List<Stock> stockList = new ArrayList<>();

        stockList.add(Stock.builder()
                .code("MECH")
                .name("Mechanical engineering INC")
                .price(new BigDecimal("120"))
                .build());

        stockList.add(Stock.builder()
                .code("VOLT")
                .name("Electronic industry INC")
                .price(new BigDecimal("110"))
                .build());

        stockList.add(Stock.builder()
                .code("ABC")
                .name("Alphabet INC")
                .price(new BigDecimal("20"))
                .build());

        stockList.add(Stock.builder()
                .code("BTEC")
                .name("Blockchain technologies LTD")
                .price(new BigDecimal("65"))
                .build());

        stockList.add(Stock.builder()
                .code("MILK")
                .name("Dairy productions INC")
                .price(new BigDecimal("75"))
                .build());

        stockList.add(Stock.builder()
                .code("FIRE")
                .name("Finance research LTD")
                .price(new BigDecimal("50"))
                .build());

        stockList.add(Stock.builder()
                .code("AGCU")
                .name("Agricultural Productions INC")
                .price(new BigDecimal("70"))
                .build());

        stockList.add(Stock.builder()
                .code("MEAT")
                .name("Butcher meat productions INC")
                .price(new BigDecimal("80"))
                .build());

        stockList.add(Stock.builder()
                .code("SOSC")
                .name("Social science INC")
                .price(new BigDecimal("35"))
                .build());

        stockList.add(Stock.builder()
                .code("FERT")
                .name("Fertilizer productions INC")
                .price(new BigDecimal("95"))
                .build());

        stockRepository.saveAll(stockList);
    }
}
