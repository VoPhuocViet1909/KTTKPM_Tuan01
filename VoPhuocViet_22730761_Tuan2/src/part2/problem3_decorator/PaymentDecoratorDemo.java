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

        // Kịch bản 3: Thẻ tín dụng + Phí xử lý + Giảm giá
        // Thứ tự bọc quan trọng: Giảm giá trước hay tính phí trước tùy thuộc logic nghiệp vụ
        // Ở đây ta bọc: (Card + Phí) -> sau đó bọc Giảm giá ra ngoài
        System.out.println("\n--- Giao dịch 3: Full Options ---");
        PaymentMethod complexPayment = new DiscountDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment(), 5.0),
            15.0
        );
        // Logic: 
        // 1. DiscountDecorator gọi super.pay(100 - 15) = pay(85)
        // 2. ProcessingFeeDecorator nhận 85, cộng phí 5 -> gọi super.pay(85 + 5) = pay(90)
        // 3. CreditCardPayment nhận 90 -> In ra
        
        complexPayment.pay(100.0);
    }
}
