package ru.dreadblade.stockmarket.notificationservice.domain;


import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Stock stock;

    private BigDecimal atPrice;
    private String userId;

    @Builder.Default
    private Boolean isActive = true;
}
