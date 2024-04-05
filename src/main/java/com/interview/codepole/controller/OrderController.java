package com.interview.codepole.controller;

import com.google.common.base.Strings;
import com.interview.codepole.validator.DateTimeValidator;
import com.interview.codepole.api.OrderRequestV1;
import com.interview.codepole.api.TotalValueResponseV1;
import com.interview.codepole.api.UserOrderResponseV1;
import com.interview.codepole.service.OrderServiceV1;
import com.interview.codepole.validator.UUIDValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static com.interview.codepole.validator.DateTimeValidator.FORMATTER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/order")
@Slf4j
public class OrderController {

    private final OrderServiceV1 orderService;


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public UUID createOrder(@RequestHeader("user-id") String userId, @RequestBody OrderRequestV1 requestV1) {
        log.info("Creating order for user {}", userId);
        checkArgument(requestV1 != null);
        checkArgument(!Strings.isNullOrEmpty(userId));
        checkArgument(UUIDValidator.isValid(userId), "User id is not a valid UUID");
        for (var item : requestV1.items()) {
            checkArgument(item.amount() > 0, "Item amount is not valid");
            checkArgument(item.price() > 0, "Item price is not valid");
            checkArgument(!Strings.isNullOrEmpty(item.name()), "Item name is empty");
            checkArgument(UUIDValidator.isValid(item.id()), "Product id is not a valid UUID");
        }
        return orderService.createOrder(UUID.fromString(userId), requestV1);
    }


    @GetMapping(value = "/total", produces = MediaType.APPLICATION_JSON_VALUE)
    public TotalValueResponseV1 getTotalOrderValue(@RequestHeader("user-id") String userId,
                                                   @RequestParam("from") String from,
                                                   @RequestParam("to") String to) {
        log.info("Getting total order value for user {} with time range from: {}, to: {}", userId, from, to);
        checkArgument(!Strings.isNullOrEmpty(userId), "User id is empty");
        checkArgument(UUIDValidator.isValid(userId), "User id is not a valid UUID");
        checkArgument(DateTimeValidator.isValid(from), "From date is not a valid date");
        checkArgument(DateTimeValidator.isValid(to), "To date is not a valid date");
        checkArgument(LocalDateTime.parse(from, FORMATTER).isBefore(LocalDateTime.parse(to, FORMATTER)));
        return orderService.getTotalOrderValue(UUID.fromString(userId), LocalDateTime.parse(from, FORMATTER), LocalDateTime.parse(to, FORMATTER));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public UserOrderResponseV1 getOrdersOfUser(@RequestHeader("user-id") String userId) {
        log.info("Getting orders for user {}", userId);
        checkArgument(!Strings.isNullOrEmpty(userId), "User id is empty");
        checkArgument(UUIDValidator.isValid(userId), "User id is not a valid UUID");
        return orderService.getOrdersOfUser(UUID.fromString(userId));
    }

}
