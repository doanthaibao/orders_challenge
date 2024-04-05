package com.interview.codepole.repository.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "order_details")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(nullable = false)
    private Integer amount;

    @Column(name = "price_at_purchase", nullable = false)
    private Double priceAtPurchase;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

 }
