package com.interview.codepole.validator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DateTimeValidatorTest {
    @Test
    public void testIsValid() {
        String validDateString = "2022-01-01 12:00:00";
        assertTrue(DateTimeValidator.isValid(validDateString));

        String invalidDateString = "2022-01-32 12:00:00";
        assertFalse(DateTimeValidator.isValid(invalidDateString));

        String nonDateString = "not a date";
        assertFalse(DateTimeValidator.isValid(nonDateString));
    }
}
