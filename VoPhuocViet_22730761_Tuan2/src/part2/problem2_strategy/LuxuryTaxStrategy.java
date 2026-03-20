package part2.problem2_strategy;

public class LuxuryTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế xa xỉ 20% + VAT 10% trên tổng giá trị
        return (price * 0.20) + (price * 0.10);
    }
}
