package ru.dreadblade.stockmarket.orderservice.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id_sequence")
    @SequenceGenerator(name = "order_id_sequence", allocationSize = 1)
    private Long id;

    private BigDecimal pricePerStock;
    private Long currentQuantity;
    private Long initialQuantity;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @ManyToOne(fetch = FetchType.EAGER)
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER)
    private Stock stock;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus = OrderStatus.CREATED;

    private Instant closedAt;

    @CreationTimestamp
    @Column(updatable = false)
    private Instant createdAt;
}
