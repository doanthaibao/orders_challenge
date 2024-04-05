package com.interview.codepole.repository;

import com.interview.codepole.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {

     @Query("SELECT o FROM Order o WHERE o.userId = :user_id")
     List<Order> getAllOrdersByUserId(@Param("user_id") UUID userId);

     @Query("SELECT SUM(o.total) FROM Order o WHERE o.userId = :user_id AND o.date BETWEEN :from AND :to")
     Optional<BigDecimal> getTotalAmountByUserIdAndDate(@Param("user_id") UUID userId, LocalDateTime from, LocalDateTime to);

}
