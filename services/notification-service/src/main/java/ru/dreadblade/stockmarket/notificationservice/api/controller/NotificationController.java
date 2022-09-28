package ru.dreadblade.stockmarket.notificationservice.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationRequestDTO;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationResponseDTO;
import ru.dreadblade.stockmarket.notificationservice.service.NotificationService;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<NotificationResponseDTO> findAllActive(@AuthenticationPrincipal Jwt jwt) {
        return notificationService.findAllActiveByUserId(jwt.getSubject());
    }

    @PostMapping
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.CREATED)
    public NotificationResponseDTO createNotification(
            @RequestBody NotificationRequestDTO requestDTO,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return notificationService.createNotification(requestDTO, jwt.getSubject());
    }

    @DeleteMapping("/{notificationId}")
    @PreAuthorize("isAuthenticated()")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deactivateNotification(
            @PathVariable Long notificationId,
            @AuthenticationPrincipal Jwt jwt
    ) {
        notificationService.deactivateNotification(notificationId, jwt.getSubject());
    }
}
