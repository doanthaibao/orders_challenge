package com.interview.codepole.service;

import com.interview.codepole.api.*;
import com.interview.codepole.domain.DiscountMaker;
import com.interview.codepole.repository.OrderRepository;
import com.interview.codepole.repository.UserRepository;
import com.interview.codepole.repository.entity.Order;
import com.interview.codepole.repository.entity.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;


@Service
@RequiredArgsConstructor
public class OrderServiceV1 {

    private final DiscountMaker discountMaker;
    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    private void validateUserId(UUID userId) {
       checkArgument(userRepository.findUserById(userId).isPresent(), "User not found");
    }


    public UUID createOrder(UUID userId, OrderRequestV1 requestV1) {
        validateUserId(userId);
        double total = requestV1.items().stream().mapToDouble(item -> {
            double price = item.price() * item.amount();
            double discount = discountMaker.makeDiscount(item.amount());
            return price - price * discount;
        }).sum();

        var order = Order.builder().date(LocalDateTime.now())
                .userId(userId)
                .total(BigDecimal.valueOf(total)).build();
        var orderDetails = requestV1.items().stream().map(item -> OrderDetail.builder()
                .amount(item.amount())
                .priceAtPurchase(item.price())
                .productId(UUID.fromString(item.id()))
                .name(item.name())
                .order(order)
                .build()).toList();
        order.setOrderDetails(orderDetails);
        var newOrder = orderRepository.save(order);
        return newOrder.getId();
    }

    public TotalValueResponseV1 getTotalOrderValue(UUID userId, LocalDateTime from, LocalDateTime to) {
        validateUserId(userId);
        var total = orderRepository.getTotalAmountByUserIdAndDate(userId, from, to).orElse(BigDecimal.ZERO);
        return new TotalValueResponseV1(userId, from, to, total);
    }

    public UserOrderResponseV1 getOrdersOfUser(UUID userId) {
        validateUserId(userId);
        var orders = orderRepository.getAllOrdersByUserId(userId).stream().map(order -> {
            var items = order.getOrderDetails().stream()
                    .map(orderDetail ->
                            new ItemResponseV1(orderDetail.getId(),
                                    orderDetail.getName(), orderDetail.getProductId(),
                                    orderDetail.getPriceAtPurchase(),
                                    orderDetail.getAmount())).toList();
            return new OrderResponseV1(order.getId(), order.getTotal(), order.getDate(), items);
        }).toList();
        return new UserOrderResponseV1(userId, orders);
    }
}
