package com.interview.codepole.api;

import java.util.List;

public record OrderRequestV1(List<ItemRequestV1> items) {
}
