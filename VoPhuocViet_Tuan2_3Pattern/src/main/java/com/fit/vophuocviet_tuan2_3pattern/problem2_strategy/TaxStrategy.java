package com.fit.vophuocviet_tuan2_3pattern.problem2_strategy;

/**
 * Strategy Interface cho tinh toan thue san pham
 *
 * PHAN TICH:
 * - Bai toan yeu cau thuat toan tinh thue thay doi linh hoat tuy loai san pham
 * - Su dung STRATEGY PATTERN vi:
 *   1. Cac thuat toan khac nhau (VAT, Tieu thu, Xa xu) co the chuyen doi
 *   2. De them thuat toan moi ma khong phai sua code cu
 *   3. Tranh if-else de chon loai thue
 */
public interface TaxStrategy {
    double calculateTax(double price);
    String getTaxName();
}

