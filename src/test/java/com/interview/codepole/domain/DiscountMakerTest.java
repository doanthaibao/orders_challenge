package com.interview.codepole.domain;

import com.interview.codepole.repository.DiscountRepository;
import com.interview.codepole.repository.entity.Discount;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class DiscountMakerTest {

    private DiscountMaker discountMaker;

    @Mock
    private DiscountRepository discountRepository;

    @BeforeEach
    void setup() {
        discountRepository = Mockito.mock(DiscountRepository.class);
        discountMaker = new DiscountMakerImpl(discountRepository);
        Mockito.when(discountRepository.getAllDiscount())
                .thenReturn(
                        List.of(
                                Discount.builder().amount(5).percent(0.1).build(),
                                Discount.builder().amount(10).percent(0.15).build()));
    }

    @AfterEach
    void tearDown() {
        discountMaker = null;
    }

    @ParameterizedTest
    @DisplayName("Throw exception on negative amount of product")
    @CsvSource({"-1", "0"})
    void shouldThrowIllegalArgumentExceptionOnNegativeAmountOfProduct(int numOfProduct) {
        assertThrows(IllegalArgumentException.class, () -> discountMaker.makeDiscount(numOfProduct));
    }

    @ParameterizedTest
    @DisplayName("Discount 0% with less than product")
    @CsvSource({"1", "2", "3", "4"})
    void shouldDiscount10PercentWithLessThan5Product(int numOfProduct) {

        Double discount = discountMaker.makeDiscount(numOfProduct);
        assertEquals(0.0, discount);
    }

    @ParameterizedTest
    @DisplayName("Discount 10%")
    @CsvSource({"5", "6", "7", "8", "9"})
    void shouldDiscount10PercentWith5Product(int numOfProduct) {
        Double discount = discountMaker.makeDiscount(numOfProduct);
        assertEquals(0.1, discount);
    }

    @ParameterizedTest
    @DisplayName("Discount 15%")
    @CsvSource({"10", "11", "12", "13", "14"})
    void shouldDiscount10PercentWithMoreThan10Product(int numOfProduct) {
        Double discount = discountMaker.makeDiscount(numOfProduct);
        assertEquals(0.15, discount);
    }
}
