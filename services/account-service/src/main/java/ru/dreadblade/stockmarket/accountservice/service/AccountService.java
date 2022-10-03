package ru.dreadblade.stockmarket.accountservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.accountservice.api.mapper.AccountMapper;
import ru.dreadblade.stockmarket.accountservice.api.mapper.StockOnAccountMapper;
import ru.dreadblade.stockmarket.accountservice.api.model.AccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.api.model.StockOnAccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.config.KafkaTopics;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.event.AccountCreatedIntegrationEvent;
import ru.dreadblade.stockmarket.accountservice.repository.AccountRepository;
import ru.dreadblade.stockmarket.accountservice.repository.StockOnAccountRepository;
import ru.dreadblade.stockmarket.shared.event.bus.EventBus;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final StockOnAccountRepository stockOnAccountRepository;

    private final KafkaTopics kafkaTopics;
    private final EventBus eventBus;

    private final AccountMapper accountMapper;
    private final StockOnAccountMapper stockOnAccountMapper;

    public List<AccountResponseDTO> findAllByOwnerId(String ownerId) {
        return accountRepository.findAllByOwnerId(ownerId).stream()
                .map(accountMapper::mapEntityToResponseDTO)
                .toList();
    }

    public List<StockOnAccountResponseDTO> findAllStocksOnAccount(Long accountId, String userId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(account.getOwnerId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        return stockOnAccountRepository.findAllByAccount(account).stream()
                .map(stockOnAccountMapper::mapEntityToResponseDTO)
                .toList();
    }

    public AccountResponseDTO createAccount(String ownerId) {
        Account account = Account.builder()
                .ownerId(ownerId)
                .build();

        account = accountRepository.save(account);

        var accountCreatedIntegrationEvent = AccountCreatedIntegrationEvent.builder()
                .accountId(account.getId())
                .ownerId(account.getOwnerId())
                .build();

        eventBus.publish(kafkaTopics.getAccountCreated(), accountCreatedIntegrationEvent);

        return accountMapper.mapEntityToResponseDTO(account);
    }
}
