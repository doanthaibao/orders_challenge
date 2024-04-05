package com.interview.codepole.api;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;


public record ItemResponseV1(int id,
                             String name,
                             @JsonProperty("product_id")
                             UUID productId,
                             double price,
                             int amount) {
}
