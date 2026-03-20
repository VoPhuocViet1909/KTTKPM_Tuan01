package com.fit.vophuocviet_tuan2_3pattern.problem3_decorator;

/**
 * Payment Interface - Co ban cho Payment Methods
 *
 * PHAN TICH:
 * - Bai toan yeu cau boc them cac tinh nang (phi xu ly, ma giam gia) vao thanh toan goc
 * - Su dung DECORATOR PATTERN vi:
 *   1. Can them responsibility dong cho payment methods
 *   2. Co the ket hop nhieu decorator (Credit Card + Processing Fee + Discount)
 *   3. Tranh class explosion (CreditCardWithFeeAndDiscount)
 */
public interface PaymentMethod {
    double calculateAmount(double amount);
    String getPaymentDetails();
}

