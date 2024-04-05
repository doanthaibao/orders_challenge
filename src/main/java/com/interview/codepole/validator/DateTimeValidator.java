package com.interview.codepole.validator;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;



public class DateTimeValidator {
    public static final String PATTERN = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    public static boolean isValid(String dateStr) {
        try {
            LocalDateTime.parse(dateStr, FORMATTER);
        } catch (DateTimeParseException e) {
            return false;
        }
        return true;
    }
}
