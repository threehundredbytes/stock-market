package ru.dreadblade.stockmarket.orderservice.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    CREATED,
    REJECTED,
    CONFIRMED,
    CLOSED;

    public static class OrderStatusConstants {
        public static final String CONFIRMED_VALUE = "CONFIRMED";
    }
}
