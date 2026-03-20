package part2.problem2_strategy;

public class ConsumptionTaxStrategy implements TaxStrategy {
    @Override
    public double calculateTax(double price) {
        // Thuế tiêu thụ 5%
        return price * 0.05;
    }
}
