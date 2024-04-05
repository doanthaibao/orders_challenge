package com.interview.codepole.repository;

import com.interview.codepole.repository.entity.Discount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@DataJpaTest
@Sql(scripts = "classpath:sql/createDiscounts.sql")
@TestPropertySource(properties = "spring.flyway.enabled=false")
class DiscountRepositoryTest {

    @Autowired
    private DiscountRepository discountRepository;

    @Test
    void loadAllDiscountsTest() {
        List<Discount> discounts = discountRepository.getAllDiscount();
        assertEquals(2, discounts.size());
    }
}
