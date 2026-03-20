package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

import org.springframework.stereotype.Component;

/**
 * DELIVERED State - Don hang da duoc giao
 */
@Component
public class DeliveredOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("[DELIVERED STATE] Don hang da giao roi, khong the xu ly!");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[DELIVERED STATE] Don hang da giao roi!");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[DELIVERED STATE] Khong the huy! Don hang da giao.");
    }

    @Override
    public String getStatus() {
        return "DELIVERED";
    }
}

