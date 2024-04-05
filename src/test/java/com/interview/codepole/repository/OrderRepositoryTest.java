package com.interview.codepole.repository;

import com.interview.codepole.repository.entity.Order;
import com.interview.codepole.repository.entity.OrderDetail;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@Sql(scripts = "classpath:sql/createOrders.sql")
@TestPropertySource(properties =
        {
                "spring.flyway.enabled=false",
                "spring.jpa.hibernate.ddl-auto=create",
                "spring.datasource.driver-class-name=org.h2.Driver",
                "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1",
                "spring.datasource.username=sa",
                "spring.datasource.password=password"
        })
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Test
    void loadAllOrdersTest() {
        UUID uuid = UUID.fromString("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1");
        List<Order> orders = orderRepository.getAllOrdersByUserId(uuid);
        assertEquals(1, orders.size());
        assertEquals(2, orders.get(0).getOrderDetails().size());
    }

    @Test
    void getAmountByUserIdAndDateTest() {
        UUID userId = UUID.fromString("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e2");
        var result = orderRepository.getTotalAmountByUserIdAndDate(userId,
                LocalDateTime.parse("2024-04-04 00:00:00", FORMATTER),
                LocalDateTime.parse("2024-04-05 00:00:00", FORMATTER));
        assertThat(result).isPresent();
        assertThat(result.get()).isEqualByComparingTo(BigDecimal.valueOf(100.0));
    }

    @Test
    void persistAnOrderTest() {

        Order order = Order.builder()
                .id(UUID.randomUUID())
                .userId(UUID.fromString("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e3"))
                .total(BigDecimal.valueOf(100.0))
                .date(LocalDateTime.now())
                .build();
        var orderDetails = OrderDetail.builder()
                .id(5)
                .name("Order 1")
                .productId(UUID.fromString("28962655-a812-40fc-b223-9e3f7e04c14d"))
                .amount(1)
                .order(order)
                .priceAtPurchase(10.0)
                .build();
        order.setOrderDetails(List.of(orderDetails));
        orderRepository.save(order);
        List<Order> orders = orderRepository.getAllOrdersByUserId(UUID.fromString("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e3"));
        assertFalse(orders.isEmpty());
        assertEquals(1, orders.get(0).getOrderDetails().size());
    }
}
