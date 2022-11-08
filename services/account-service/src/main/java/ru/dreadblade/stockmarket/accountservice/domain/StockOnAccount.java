package ru.dreadblade.stockmarket.accountservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class StockOnAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    private Stock stock;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    @Builder.Default
    private Long quantity = 0L;

    @Builder.Default
    private Long reservedQuantity = 0L;

    @CreationTimestamp
    private Instant purchasedAt;
}
