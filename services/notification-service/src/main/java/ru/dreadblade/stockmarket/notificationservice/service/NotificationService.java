package ru.dreadblade.stockmarket.notificationservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.dreadblade.stockmarket.notificationservice.api.mapper.NotificationMapper;
import ru.dreadblade.stockmarket.notificationservice.domain.Notification;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationRequestDTO;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationResponseDTO;
import ru.dreadblade.stockmarket.notificationservice.repository.NotificationRepository;
import ru.dreadblade.stockmarket.notificationservice.repository.StockRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final StockRepository stockRepository;
    private final NotificationMapper notificationMapper;

    public List<NotificationResponseDTO> findAllActiveByUserId(String userId) {
        return notificationRepository.findAllByUserIdAndIsActive(userId, true).stream()
                .map(notificationMapper::mapEntityToResponseDTO)
                .toList();
    }

    public NotificationResponseDTO createNotification(NotificationRequestDTO requestDTO, String userId) {
        var stock = stockRepository.findById(requestDTO.stockId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        var notification = Notification.builder()
                .stock(stock)
                .atPrice(requestDTO.atPrice())
                .userId(userId)
                .build();

        notification = notificationRepository.save(notification);

        return notificationMapper.mapEntityToResponseDTO(notification);
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
