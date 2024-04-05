package com.interview.codepole.service;

import com.interview.codepole.api.ItemRequestV1;
import com.interview.codepole.api.OrderRequestV1;
import com.interview.codepole.domain.DiscountMaker;
import com.interview.codepole.repository.OrderRepository;
import com.interview.codepole.repository.UserRepository;
import com.interview.codepole.repository.entity.Order;
import com.interview.codepole.repository.entity.OrderDetail;
import com.interview.codepole.repository.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class OrderServiceV1Test {

    private OrderServiceV1 orderServiceV1;


    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        DiscountMaker discountMaker = (int amount) -> {
            if (amount < 0) {
                throw new IllegalArgumentException("Amount of product cannot be negative");
            }

            if (amount >= 5 && amount < 10) {
                return 0.1;
            } else if (amount >= 10) {
                return 0.15;
            }
            return 0.0;
        };
        userRepository = Mockito.mock(UserRepository.class);
        Mockito.when(userRepository.findUserById(Mockito.any())).thenReturn(java.util.Optional.of(new User()));
        orderRepository = Mockito.mock(OrderRepository.class);
        orderServiceV1 = new OrderServiceV1(discountMaker, orderRepository, userRepository);
    }


    @Test
    void shouldCreateOrder() {
        OrderRequestV1 orderRequest = defaultOrderRequest();
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(Order.builder().id(UUID.randomUUID()).build());
        UUID orderId = orderServiceV1.createOrder(UUID.randomUUID(), orderRequest);
        assertThat(orderId).isNotNull();
    }

    @Test
    void shouldDiscount10Percent() {
        OrderRequestV1 orderRequest = new OrderRequestV1(List.of(new ItemRequestV1(UUID.randomUUID().toString(), 5, 10, "Product 1")));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(Order.builder().id(UUID.randomUUID()).build());
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        UUID orderId = orderServiceV1.createOrder(UUID.randomUUID(), orderRequest);
        Mockito.verify(orderRepository).save(captor.capture());
        assertThat(orderId).isNotNull();
        assertThat(captor.getValue().getTotal()).isEqualTo(BigDecimal.valueOf(45.0));
    }

    @Test
    void shouldDiscount15Percent() {
        OrderRequestV1 orderRequest = new OrderRequestV1(List.of(new ItemRequestV1(UUID.randomUUID().toString(), 10, 10, "Product 1")));
        Mockito.when(orderRepository.save(Mockito.any())).thenReturn(Order.builder().id(UUID.randomUUID()).build());
        ArgumentCaptor<Order> captor = ArgumentCaptor.forClass(Order.class);

        UUID orderId = orderServiceV1.createOrder(UUID.randomUUID(), orderRequest);
        Mockito.verify(orderRepository).save(captor.capture());
        assertThat(orderId).isNotNull();
        assertThat(captor.getValue().getTotal()).isEqualTo(BigDecimal.valueOf(85.0));
    }

    @Test
    void shouldReturnValueOfOrder() {
        UUID userId = UUID.randomUUID();
        var localFrom = LocalDateTime.now().minusDays(1);
        var localTo = LocalDateTime.now();
        Mockito.when(orderRepository.getTotalAmountByUserIdAndDate(userId, localFrom, localTo))
                .thenReturn(java.util.Optional.of(BigDecimal.TEN));
        var value = orderServiceV1.getTotalOrderValue(userId, localFrom, localTo);
        assertThat(value).isNotNull();
        assertThat(value.totalValue()).isEqualTo(BigDecimal.TEN);
    }

    @Test
    void shouldReturnOrdersOfUser() {
        UUID userId = UUID.randomUUID();
        Mockito.when(orderRepository.getAllOrdersByUserId(userId))
                .thenReturn(List.of(Order.builder().id(UUID.randomUUID())
                                .orderDetails(
                                        List.of(
                                                OrderDetail.builder().id(1).name("Product 1")
                                                        .priceAtPurchase(10.0)
                                                        .amount(1)
                                                        .productId(UUID.randomUUID()).build()))
                        .build()));
        var orders = orderServiceV1.getOrdersOfUser(userId);
        assertThat(orders).isNotNull();
        assertThat(orders.orders()).hasSize(1);
        assertThat(orders.orders().get(0).items()).hasSize(1);
    }

    private OrderRequestV1 defaultOrderRequest() {
        return new OrderRequestV1(List.of(new ItemRequestV1(UUID.randomUUID().toString(), 1, 10, "Product 1")));
    }

}
