package ru.dreadblade.stockmarket.orderservice.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    CREATED,
    REJECTED,
    CONFIRMED,
    CLOSED;

    public static class OrderStatusConstants {
        public static final String SUBMITTED_VALUE = "CREATED";
        public static final String REJECTED_VALUE = "REJECTED";
        public static final String CONFIRMED_VALUE = "CONFIRMED";
        public static final String CLOSED_VALUE = "CLOSED";
    }
}
