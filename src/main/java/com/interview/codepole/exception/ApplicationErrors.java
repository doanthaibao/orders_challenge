package com.interview.codepole.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrors {
    ILLEGAL_ARGUMENT("ILLEGAL_ARGUMENT"),
    INTERNAL_ERROR("INTERNAL_ERROR");

    private final String value;

    ApplicationErrors(String value) {
        this.value = value;
    }
}
