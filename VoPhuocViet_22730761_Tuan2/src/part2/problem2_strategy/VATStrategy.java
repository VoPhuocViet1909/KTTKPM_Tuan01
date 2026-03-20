package part2.problem2_strategy;

public class VATStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế VAT 10%
        return price * 0.10;
    }
}
