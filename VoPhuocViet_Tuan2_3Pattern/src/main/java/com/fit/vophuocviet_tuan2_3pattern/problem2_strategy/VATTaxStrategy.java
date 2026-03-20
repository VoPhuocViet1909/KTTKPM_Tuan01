package com.fit.vophuocviet_tuan2_3pattern.problem2_strategy;

import org.springframework.stereotype.Component;

/**
 * VAT Tax Strategy - Thue gia tri gia tang (10%)
 */
@Component("vatTaxStrategy")
public class VATTaxStrategy implements TaxStrategy {
    private static final double VAT_RATE = 0.10;

    @Override
    public double calculateTax(double price) {
        return price * VAT_RATE;
    }

    @Override
    public String getTaxName() {
        return "VAT (10%)";
    }
}

