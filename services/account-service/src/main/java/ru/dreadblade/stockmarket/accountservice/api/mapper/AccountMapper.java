package ru.dreadblade.stockmarket.accountservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.accountservice.api.model.AccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.domain.Account;

@Component
public class AccountMapper {
    public AccountResponseDTO mapEntityToResponseDTO(Account account) {
        return new AccountResponseDTO(account.getId(), account.getBalance(), account.getReservedBalance());
    }
}
