package ru.dreadblade.stockmarket.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.model.AccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.model.StockOnAccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<AccountResponseDTO> findAllByUser(@AuthenticationPrincipal Jwt jwt) {
        return accountService.findAllByOwnerId(jwt.getSubject());
    }

    @GetMapping("/{accountId}/stocks")
    @PreAuthorize("isAuthenticated()")
    public List<StockOnAccountResponseDTO> findAllStocksOnAccount(
            @PathVariable Long accountId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return accountService.findAllStocksOnAccount(accountId, jwt.getSubject());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Account createAccount(@AuthenticationPrincipal Jwt jwt) {
        return accountService.createAccount(jwt.getSubject());
    }
}
