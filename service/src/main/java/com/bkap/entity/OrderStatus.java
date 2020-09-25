package com.bkap.entity;

/**
 * Project-SemIV
 *
 * @author Tung lam
 * @created_at 30/07/2020 - 09:54
 * @created_by Tung lam
 * @since 30/07/2020
 */
public enum OrderStatus {
    ORDER_PROCESSING(0), // đã tiếp nhận
    ORDER_CONFIRM(1), // đang xử lý
    ORDER_SUCCESS(2), // đã hoàn thành
    ORDER_CANCLED(3); // hủy bỏ

    private final int value;

    OrderStatus(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
