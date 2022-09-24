package ru.dreadblade.stockmarket.orderservice.domain;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Stock {
    @Id
    private Long id;
}
