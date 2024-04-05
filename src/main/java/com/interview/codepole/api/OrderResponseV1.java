package com.interview.codepole.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponseV1(UUID orderId,
                              @JsonProperty("total_amount")
                              BigDecimal totalAmount,
                              @JsonProperty("order_date")
                              LocalDateTime date,
                              @JsonProperty("user_id")
                              List<ItemResponseV1> items
) {
}
