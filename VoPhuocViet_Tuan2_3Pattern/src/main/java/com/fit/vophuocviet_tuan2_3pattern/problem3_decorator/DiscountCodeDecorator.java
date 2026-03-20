package com.fit.vophuocviet_tuan2_3pattern.problem3_decorator;

/**
 * Concrete Decorator - Them Ma giam gia (Discount Code)
 */
public class DiscountCodeDecorator extends PaymentDecorator {
    private double discountRate;

    public DiscountCodeDecorator(PaymentMethod wrappedPayment) {
        super(wrappedPayment);
        this.discountRate = 0.10; // Mac dinh 10% discount
    }

    public DiscountCodeDecorator(PaymentMethod wrappedPayment, double discountRate) {
        super(wrappedPayment);
        this.discountRate = discountRate;
    }

    @Override
    public double calculateAmount(double amount) {
        double baseAmount = wrappedPayment.calculateAmount(amount);
        double discount = baseAmount * discountRate;
        System.out.println("[DISCOUNT CODE] Giam gia (" + (int)(discountRate * 100) + "%): " + discount);
        return baseAmount - discount;
    }

    @Override
    public String getPaymentDetails() {
        return wrappedPayment.getPaymentDetails() + " + Discount Code (" + (int)(discountRate * 100) + "%)";
    }
}

