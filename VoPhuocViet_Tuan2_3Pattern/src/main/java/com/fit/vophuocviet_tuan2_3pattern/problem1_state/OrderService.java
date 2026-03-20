package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

import org.springframework.stereotype.Service;

/**
 * Service class su dung State Pattern
 * DI duoc ap dung tai day de inject cac state implementations
 */
@Service
public class OrderService {

    /**
     * Xu ly don hang theo cac state khac nhau
     */
    public void processOrder(Order order) {
        System.out.println("\n=== Xu ly Order: " + order.getOrderId() + " ===");
        order.process();
    }

    public void deliverOrder(Order order) {
        System.out.println("\n=== Giao Order: " + order.getOrderId() + " ===");
        order.deliver();
    }

    public void cancelOrder(Order order) {
        System.out.println("\n=== Huy Order: " + order.getOrderId() + " ===");
        order.cancel();
    }

    public String getOrderStatus(Order order) {
        return order.getStatus();
    }
}

