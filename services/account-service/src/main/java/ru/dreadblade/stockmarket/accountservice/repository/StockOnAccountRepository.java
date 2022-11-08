package ru.dreadblade.stockmarket.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.domain.Stock;
import ru.dreadblade.stockmarket.accountservice.domain.StockOnAccount;

import java.util.List;
import java.util.Optional;

public interface StockOnAccountRepository extends JpaRepository<StockOnAccount, Long> {
    List<StockOnAccount> findAllByAccount(Account account);
    Optional<StockOnAccount> findByAccountAndStock(Account account, Stock stock);
}
