package com.fit.vophuocviet_tuan2_3pattern.problem3_decorator;

/**
 * Abstract Decorator class - Base cho tất cả các decorator
 */
public abstract class PaymentDecorator implements PaymentMethod {
    protected PaymentMethod wrappedPayment;

    public PaymentDecorator(PaymentMethod wrappedPayment) {
        this.wrappedPayment = wrappedPayment;
    }

    @Override
    public double calculateAmount(double amount) {
        return wrappedPayment.calculateAmount(amount);
    }

    @Override
    public String getPaymentDetails() {
        return wrappedPayment.getPaymentDetails();
    }
}

