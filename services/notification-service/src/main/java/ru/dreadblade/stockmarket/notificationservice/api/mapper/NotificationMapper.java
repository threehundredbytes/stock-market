package ru.dreadblade.stockmarket.notificationservice.api.mapper;

import org.springframework.stereotype.Component;
import ru.dreadblade.stockmarket.notificationservice.api.model.NotificationResponseDTO;
import ru.dreadblade.stockmarket.notificationservice.domain.Notification;

@Component
public class NotificationMapper {
    public NotificationResponseDTO mapEntityToResponseDTO(Notification notification) {
        return new NotificationResponseDTO(notification.getId(), notification.getAtPrice(), notification.getStock().getId());
    }
}
