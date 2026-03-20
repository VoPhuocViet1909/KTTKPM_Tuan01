package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

import org.springframework.stereotype.Component;

/**
 * CANCELLED State - Don hang da bi huy
 */
@Component
public class CancelledOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("[CANCELLED STATE] Khong the xu ly! Don hang da huy.");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[CANCELLED STATE] Khong the giao! Don hang da huy.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[CANCELLED STATE] Don hang da huy roi!");
    }

    @Override
    public String getStatus() {
        return "CANCELLED";
    }
}

