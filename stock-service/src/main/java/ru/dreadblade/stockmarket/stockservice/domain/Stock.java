package ru.dreadblade.stockmarket.stockservice.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "stock_id_sequence")
    @SequenceGenerator(name = "stock_id_sequence", allocationSize = 1)
    private Long id;

    private String name;
    private String code;
    private BigDecimal price;

    @Builder.Default
    private Instant createdAt = Instant.now();

    @Builder.Default
    private Instant updatedAt = Instant.now();
}
