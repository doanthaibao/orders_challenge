package com.interview.codepole.api;

import java.util.List;
import java.util.UUID;

public record UserOrderResponseV1(UUID userId, List<OrderResponseV1> orders) {
}

