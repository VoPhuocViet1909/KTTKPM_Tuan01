package com.fit.vophuocviet_tuan2_3pattern.problem3_decorator;

/**
 * Concrete Decorator - Them Phi xu ly (Processing Fee)
 */
public class ProcessingFeeDecorator extends PaymentDecorator {
    private static final double PROCESSING_FEE_RATE = 0.02; // 2%

    public ProcessingFeeDecorator(PaymentMethod wrappedPayment) {
        super(wrappedPayment);
    }

    @Override
    public double calculateAmount(double amount) {
        double baseAmount = wrappedPayment.calculateAmount(amount);
        double fee = baseAmount * PROCESSING_FEE_RATE;
        System.out.println("[PROCESSING FEE] Phi xu ly (2%): " + fee);
        return baseAmount + fee;
    }

    @Override
    public String getPaymentDetails() {
        return wrappedPayment.getPaymentDetails() + " + Processing Fee (2%)";
    }
}

