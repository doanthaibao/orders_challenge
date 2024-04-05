package com.interview.codepole.repository;

import com.interview.codepole.repository.entity.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface DiscountRepository extends JpaRepository<Discount, UUID> {
    @Query("SELECT d FROM Discount d")
    List<Discount> getAllDiscount();
}
