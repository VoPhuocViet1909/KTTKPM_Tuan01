package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

/**
 * State Interface cho he thong quan ly don hang
 *
 * PHAN TICH:
 * - Bai toan yeu cau hanh vi cua Order thay doi tuy vao state hien tai
 * - Su dung STATE PATTERN vi:
 *   1. Behavior thay doi theo state (New -> Processing -> Delivered -> Cancelled)
 *   2. Moi state co hanh dong rieng (process, deliver, cancel)
 *   3. Tranh if-else dai, de bao tri
 */
public interface OrderState {
    void process(Order order);
    void deliver(Order order);
    void cancel(Order order);
    String getStatus();
}

