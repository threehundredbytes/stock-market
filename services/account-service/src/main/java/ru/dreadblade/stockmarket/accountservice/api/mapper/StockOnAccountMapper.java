package ru.dreadblade.stockmarket.accountservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.api.model.StockOnAccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.domain.StockOnAccount;

@Component
public class StockOnAccountMapper {
    public StockOnAccountResponseDTO mapEntityToResponseDTO(StockOnAccount stockOnAccount) {
        return new StockOnAccountResponseDTO(stockOnAccount.getStock().getId(), stockOnAccount.getQuantity(),
                stockOnAccount.getReservedQuantity(), stockOnAccount.getPurchasedAt());
    }
}
