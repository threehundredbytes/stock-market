package ru.dreadblade.stockmarket.orderservice.domain;

public enum OrderType {
    PURCHASE,
    SALE;

    public static class OrderTypeConstants {
        public static final String PURCHASE_VALUE = "PURCHASE";
        public static final String SALE_VALUE = "SALE";
    }
}
