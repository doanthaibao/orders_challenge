package com.interview.codepole.api;

public record ErrorResponseV1(String message, String code) {
    public static ErrorResponseV1 from(String message, String code) {
        return new ErrorResponseV1(message, code);
    }
}
