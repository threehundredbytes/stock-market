package ru.dreadblade.stockmarket.accountservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dreadblade.stockmarket.accountservice.domain.Account;
import ru.dreadblade.stockmarket.accountservice.service.AccountService;

import java.util.List;

@RestController
@RequestMapping("/accounts")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<Account> findAllByUser(@AuthenticationPrincipal Jwt jwt) {
        return accountService.findAllByOwnerId(jwt.getSubject());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public Account createAccount(@AuthenticationPrincipal Jwt jwt) {
        return accountService.createAccount(jwt.getSubject());
    }
}
