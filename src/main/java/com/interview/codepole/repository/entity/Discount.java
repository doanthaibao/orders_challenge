package com.interview.codepole.repository.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "discounts")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Discount {

    @Id
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private Double percent;
}