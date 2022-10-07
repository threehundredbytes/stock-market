package ru.dreadblade.stockmarket.accountservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.accountservice.api.model.AccountResponseDTO;
import ru.dreadblade.stockmarket.accountservice.api.model.StockOnAccountResponseDTO;
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

    @GetMapping("/{accountId}")
    @PreAuthorize("isAuthenticated()")
    public AccountResponseDTO findByAccountId(
            @PathVariable Long accountId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return accountService.findByAccountId(accountId, jwt.getSubject());
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
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponseDTO createAccount(@AuthenticationPrincipal Jwt jwt) {
        return accountService.createAccount(jwt.getSubject());
    }
}
