package part2.problem3_decorator;

// Main Demo
public class PaymentDecoratorDemo {
    public static void main(String[] args) {
        System.out.println("=== Demo Decorator Pattern (Thanh toán mở rộng) ===");

        // Kịch bản 1: Thanh toán thẻ tín dụng bình thường
        System.out.println("\n--- Giao dịch 1: Thẻ tín dụng thường ---");
        PaymentMethod basicCard = new CreditCardPayment();
        basicCard.pay(100.0);

        // Kịch bản 2: PayPal + Phí xử lý
        System.out.println("\n--- Giao dịch 2: PayPal có phí ---");
        PaymentMethod paypalWithFee = new ProcessingFeeDecorator(new PayPalPayment(), 5.0);
        paypalWithFee.pay(100.0);

        System.out.println("\n--- Giao dịch 3: Full Options ---");
        PaymentMethod complexPayment = new DiscountDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment(), 5.0),
            15.0
        );
        
        complexPayment.pay(100.0);
    }
}
