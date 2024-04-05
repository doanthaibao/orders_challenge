package com.interview.codepole.repository.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@Entity
@Table(name = "orders")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "total", nullable = false)
    private BigDecimal total;

    @Column(name = "date", nullable = false, columnDefinition = "TIMESTAMP")
    private LocalDateTime date;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderDetail> orderDetails;

}