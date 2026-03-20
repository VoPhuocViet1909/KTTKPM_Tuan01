package com.fit.vophuocviet_tuan2_3pattern.problem2_strategy;

/**
 * Product class - Context sử dụng Strategy
 */
public class Product {
    private String productId;
    private String productName;
    private double price;
    private TaxStrategy taxStrategy;

    public Product(String productId, String productName, double price) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
    }

    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }

    public double calculateTotalPrice() {
        if (taxStrategy == null) {
            throw new IllegalStateException("Tax strategy not set!");
        }
        double tax = taxStrategy.calculateTax(price);
        return price + tax;
    }

    public String getProductInfo() {
        if (taxStrategy == null) {
            throw new IllegalStateException("Tax strategy not set!");
        }
        double tax = taxStrategy.calculateTax(price);
        return String.format("Sản phẩm: %s | Giá: %.2f | Thuế: %s (%.2f) | Tổng: %.2f",
                productName, price, taxStrategy.getTaxName(), tax, calculateTotalPrice());
    }

    // Getters
    public String getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public TaxStrategy getTaxStrategy() {
        return taxStrategy;
    }
}

