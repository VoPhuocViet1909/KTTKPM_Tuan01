package com.fit.vophuocviet_tuan2_3pattern.problem3_decorator;

/**
 * Concrete Component - Credit Card Payment (Thanh toan bang The Credit)
 */
public class CreditCardPayment implements PaymentMethod {

    @Override
    public double calculateAmount(double amount) {
        System.out.println("[CREDIT CARD] Thanh toan: " + amount);
        return amount;
    }

    @Override
    public String getPaymentDetails() {
        return "Credit Card Payment";
    }
}

