package ru.dreadblade.stockmarket.orderservice.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    private Long id;
    private String ticker;
    private BigDecimal price;
}
