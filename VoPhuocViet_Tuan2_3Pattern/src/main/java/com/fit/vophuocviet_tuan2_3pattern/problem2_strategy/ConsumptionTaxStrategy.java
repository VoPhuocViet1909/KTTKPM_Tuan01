package com.fit.vophuocviet_tuan2_3pattern.problem2_strategy;

import org.springframework.stereotype.Component;

/**
 * Consumption Tax Strategy - Thue tieu thu dac biet (5%)
 */
@Component("consumptionTaxStrategy")
public class ConsumptionTaxStrategy implements TaxStrategy {
    private static final double CONSUMPTION_TAX_RATE = 0.05;

    @Override
    public double calculateTax(double price) {
        return price * CONSUMPTION_TAX_RATE;
    }

    @Override
    public String getTaxName() {
        return "Consumption Tax (5%)";
    }
}

