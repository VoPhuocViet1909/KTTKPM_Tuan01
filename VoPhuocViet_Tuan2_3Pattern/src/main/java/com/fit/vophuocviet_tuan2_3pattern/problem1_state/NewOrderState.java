package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

import org.springframework.stereotype.Component;

/**
 * NEW State - Don hang vua tao
 */
@Component
public class NewOrderState implements OrderState {

    @Override
    public void process(Order order) {
        System.out.println("[NEW STATE] Xu ly don hang...");
        order.setState(new ProcessingOrderState());
    }

    @Override
    public void deliver(Order order) {
        System.out.println("[NEW STATE] Khong the giao! Don hang chua duoc xu ly.");
    }

    @Override
    public void cancel(Order order) {
        System.out.println("[NEW STATE] Huy don hang.");
        order.setState(new CancelledOrderState());
    }

    @Override
    public String getStatus() {
        return "NEW";
    }
}

