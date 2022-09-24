package ru.dreadblade.stockmarket.accountservice.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class AccountResponseDTO {
    private Long id;
    private BigDecimal balance;
    private BigDecimal reservedBalance;
}
