package com.interview.codepole.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record TotalValueResponseV1(
        @JsonProperty("user_id")
        UUID userId,
        LocalDateTime from,
        LocalDateTime to,
        @JsonProperty("total_value")
        BigDecimal totalValue
) {
}
