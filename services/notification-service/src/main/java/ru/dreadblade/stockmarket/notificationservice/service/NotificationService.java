package ru.dreadblade.stockmarket.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.notificationservice.domain.Notification;
import ru.dreadblade.stockmarket.notificationservice.model.NotificationRequestDTO;
import ru.dreadblade.stockmarket.notificationservice.model.NotificationResponseDTO;
import ru.dreadblade.stockmarket.notificationservice.repository.NotificationRepository;
import ru.dreadblade.stockmarket.notificationservice.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final StockRepository stockRepository;

    public List<NotificationResponseDTO> findAllActiveByUserId(String userId) {
        return notificationRepository.findAllByUserIdAndIsActive(userId, true).stream()
                .map(notification -> NotificationResponseDTO.builder()
                        .id(notification.getId())
                        .atPrice(notification.getAtPrice())
                        .stockId(notification.getStock().getId())
                        .build())
                .toList();
    }

    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO, String userId) {
        var stock = stockRepository.findById(requestDTO.getStockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var notification = Notification.builder()
                .stock(stock)
                .atPrice(requestDTO.getAtPrice())
                .userId(userId)
                .build();

        notificationRepository.save(notification);

        return NotificationResponseDTO.builder()
                .id(notification.getId())
                .atPrice(notification.getAtPrice())
                .stockId(notification.getStock().getId())
                .build();
    }

    public void deactivateNotification(Long notificationId, String userId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (!userId.equals(notification.getUserId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
        }

        notification.setIsActive(false);

        notificationRepository.save(notification);
    }
}
