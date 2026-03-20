package com.fit.vophuocviet_tuan2_3pattern.problem1_state;

/**
 * Context class - Quan ly Order voi cac state khac nhau
 */
public class Order {
    private String orderId;
    private OrderState state;

    public Order(String orderId) {
        this.orderId = orderId;
        this.state = new NewOrderState(); // State mac dinh
    }

    public void process() {
        state.process(this);
    }

    public void deliver() {
        state.deliver(this);
    }

    public void cancel() {
        state.cancel(this);
    }

    public String getStatus() {
        return state.getStatus();
    }

    // Getter & Setter
    public void setState(OrderState state) {
        this.state = state;
        System.out.println("=> Trang thai don hang: " + this.state.getStatus());
    }

    public String getOrderId() {
        return orderId;
    }
}

