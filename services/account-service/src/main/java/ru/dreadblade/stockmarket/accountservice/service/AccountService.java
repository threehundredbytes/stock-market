package ru.dreadblade.stockmarket.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;

    public List<Account> findAllByOwnerId(String ownerId) {
        return accountRepository.findAllByOwnerId(ownerId);
    }

    public Account createAccount(String ownerId) {
        Account account = Account.builder()
                .ownerId(ownerId)
                .balance(BigDecimal.ZERO)
                .build();

        return accountRepository.save(account);
    }
}
