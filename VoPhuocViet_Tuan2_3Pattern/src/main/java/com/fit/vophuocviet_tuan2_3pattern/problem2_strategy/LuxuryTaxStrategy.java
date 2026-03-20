package com.fit.vophuocviet_tuan2_3pattern.problem2_strategy;

import org.springframework.stereotype.Component;

/**
 * Luxury Tax Strategy - Thue xa xu (15%)
 */
@Component("luxuryTaxStrategy")
public class LuxuryTaxStrategy implements TaxStrategy {
    private static final double LUXURY_TAX_RATE = 0.15;

    @Override
    public double calculateTax(double price) {
        return price * LUXURY_TAX_RATE;
    }

    @Override
    public String getTaxName() {
        return "Luxury Tax (15%)";
    }
}

