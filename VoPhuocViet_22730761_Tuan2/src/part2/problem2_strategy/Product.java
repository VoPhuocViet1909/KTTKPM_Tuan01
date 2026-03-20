package part2.problem2_strategy;

public class Product {
    private String name;
    private double price;
    private TaxStrategy taxStrategy;

    public Product(String name, double price, TaxStrategy taxStrategy) {
        this.name = name;
        this.price = price;
        this.taxStrategy = taxStrategy;
    }

    public double calculateFinalPrice() {
        double tax = taxStrategy.calculateTax(price);
        return price + tax;
    }

    public void displayInfo() {
        double finalPrice = calculateFinalPrice();
        System.out.printf("Sản phẩm: %s | Giá gốc: %.2f | Thuế: %.2f | Tổng: %.2f%n", 
                          name, price, taxStrategy.calculateTax(price), finalPrice);
    }
}
