package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

import org.springframework.stereotype.Component;

/**
 * PROCESSING State - Don hang dang duoc xu ly
 */
@Component
public class ProcessingOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("[PROCESSING STATE] Don hang dang duoc xu ly roi!");
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[PROCESSING STATE] Giao don hang...");
        order.setState(new DeliveredOrderState());
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[PROCESSING STATE] Huy don hang.");
        order.setState(new CancelledOrderState());
    }

    @Override
    public String getStatus() {
        return "PROCESSING";
    }
}

