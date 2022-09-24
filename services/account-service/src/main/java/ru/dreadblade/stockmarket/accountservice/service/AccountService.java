package ru.dreadblade.stockmarket.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.event.AccountCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.event.bus.EventBus;
import ru.dreadblade.stockmarket.accountservice.model.AccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.model.StockOnAccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockOnAccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final StockOnAccountRepository stockOnAccountRepository;
    private final EventBus eventBus;

    public List<AccountResponseDTO> findAllByOwnerId(String ownerId) {
        return accountRepository.findAllByOwnerId(ownerId).stream()
                .map(account -> AccountResponseDTO.builder()
                        .id(account.getId())
                        .balance(account.getBalance())
                        .reservedBalance(account.getReservedBalance())
                        .build())
                .toList();
    }

    public List<StockOnAccountResponseDTO> findAllStocksOnAccount(Long accountId, String userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return stockOnAccountRepository.findAllByAccount(account).stream()
                .map(stockOnAccount -> StockOnAccountResponseDTO.builder()
                        .stockId(stockOnAccount.getStock().getId())
                        .quantity(stockOnAccount.getQuantity())
                        .reservedQuantity(stockOnAccount.getReservedQuantity())
                        .build())
                .toList();
    }

    public Account createAccount(String ownerId) {
        Account account = Account.builder()
                .ownerId(ownerId)
                .build();

        account = accountRepository.save(account);

        var accountCreatedIntegrationEvent = AccountCreatedIntegrationEvent.builder()
                .accountId(account.getId())
                .ownerId(account.getOwnerId())
                .build();

        eventBus.publish("account-created", accountCreatedIntegrationEvent);

        return account;
    }
}
