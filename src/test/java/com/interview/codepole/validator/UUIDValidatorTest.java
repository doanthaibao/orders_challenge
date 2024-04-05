package com.interview.codepole.validator;

import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UUIDValidatorTest {

    @Test
    public void testIsValid() {
        String validUUID = UUID.randomUUID().toString();
        assertTrue(UUIDValidator.isValid(validUUID));
        String invalidUUID = "invalid uuid";
        assertFalse(UUIDValidator.isValid(invalidUUID));
    }
}
