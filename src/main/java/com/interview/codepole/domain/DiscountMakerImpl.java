package com.interview.codepole.domain;

import com.interview.codepole.repository.DiscountRepository;
import com.interview.codepole.repository.entity.Discount;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;

@RequiredArgsConstructor
@Component
public class DiscountMakerImpl implements DiscountMaker {

    private final DiscountRepository discountRepository;

    private static List<Discount> DISCOUNTS;

    @Override
    public Double makeDiscount(int amountOfProduct) {

        if (DISCOUNTS == null) {
            DISCOUNTS = new ArrayList<>(discountRepository.getAllDiscount())
                    .stream().sorted((d1, d2) -> d2.getAmount().compareTo(d1.getAmount())).toList();
        }

        checkArgument(amountOfProduct > 0, "amount of product must be positive");

        for (Discount discount : DISCOUNTS) {
            if (amountOfProduct >= discount.getAmount()) {
                return discount.getPercent();
            }
        }
        return 0.0;
    }
}
