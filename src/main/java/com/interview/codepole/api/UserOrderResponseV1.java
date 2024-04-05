package com.interview.codepole.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record UserOrderResponseV1(@JsonProperty("user_id") UUID userId, List<OrderResponseV1> orders) {
}

