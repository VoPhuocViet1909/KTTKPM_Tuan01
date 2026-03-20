package com.fit.vophuocviet_tuan2_3pattern;

import com.fit.vophuocviet_tuan2_3pattern.problem1_state.Order;
import com.fit.vophuocviet_tuan2_3pattern.problem1_state.OrderService;
import com.fit.vophuocviet_tuan2_3pattern.problem2_strategy.Product;
import com.fit.vophuocviet_tuan2_3pattern.problem2_strategy.TaxStrategy;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.CreditCardPayment;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.DiscountCodeDecorator;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.PaymentMethod;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.ProcessingFeeDecorator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * CommandLineRunner - Chạy code khi Spring Boot khởi động
 * (Thay thế cho main() console thông thường)
 */
@Component
public class DesignPatternDemo implements CommandLineRunner {

    @Autowired
    private OrderService orderService;

    @Autowired
    @Qualifier("vatTaxStrategy")
    private TaxStrategy vatStrategy;

    @Autowired
    @Qualifier("consumptionTaxStrategy")
    private TaxStrategy consumptionStrategy;

    @Autowired
    @Qualifier("luxuryTaxStrategy")
    private TaxStrategy luxuryStrategy;

    @Override
    public void run(String... args) {
        System.out.println("\n");
        System.out.println("=".repeat(80));
        System.out.println("DEMO: 3 DESIGN PATTERN TRONG SPRING BOOT");
        System.out.println("=".repeat(80));

        demonstrateStatePattern();
        System.out.println("\n");

        demonstrateStrategyPattern();
        System.out.println("\n");

        demonstrateDecoratorPattern();
        System.out.println("\n");

        System.out.println("=".repeat(80));
        System.out.println("KẾT LUẬN: Lợi ích kết hợp Pattern với DI của Spring Boot");
        System.out.println("=".repeat(80));
        printConclusions();
    }

    // ========== BÀI TOÁN 1: STATE PATTERN ==========
    private void demonstrateStatePattern() {
        System.out.println("\n┌─── BÀI TOÁN 1: STATE PATTERN ───┐");
        System.out.println("│ Hệ thống quản lý đơn hàng        │");
        System.out.println("└──────────────────────────────────┘\n");

        System.out.println("PHÂN TÍCH:");
        System.out.println("• Bài toán: Hành vi Order thay đổi tùy trạng thái (New, Processing, Delivered, Cancelled)");
        System.out.println("• Pattern: STATE PATTERN");
        System.out.println("• Lý do: Behavior linh hoạt theo state, dễ thêm state mới, tránh if-else\n");

        Order order = new Order("ORD-001");
        System.out.println("DEMO:");
        System.out.println("1. Tạo đơn hàng mới:");
        System.out.println("   Status: " + order.getStatus());

        System.out.println("\n2. Process đơn hàng (NEW -> PROCESSING):");
        orderService.processOrder(order);

        System.out.println("\n3. Deliver đơn hàng (PROCESSING -> DELIVERED):");
        orderService.deliverOrder(order);

        System.out.println("\n4. Cố gắng cancel đơn hàng đã giao (không thể):");
        orderService.cancelOrder(order);

        System.out.println("\nDEMO 2 - Hủy từ NEW state:");
        Order order2 = new Order("ORD-002");
        System.out.println("Order: " + order2.getOrderId() + ", Status: " + order2.getStatus());
        orderService.cancelOrder(order2);
        System.out.println("Status sau hủy: " + order2.getStatus());
    }

    // ========== BÀI TOÁN 2: STRATEGY PATTERN ==========
    private void demonstrateStrategyPattern() {
        System.out.println("\n┌─── BÀI TOÁN 2: STRATEGY PATTERN ───┐");
        System.out.println("│ Tính toán thuế cho sản phẩm     │");
        System.out.println("└──────────────────────────────────┘\n");

        System.out.println("PHÂN TÍCH:");
        System.out.println("• Bài toán: Thuật toán tính thuế thay đổi linh hoạt (VAT, Tiêu thụ, Xa xỉ)");
        System.out.println("• Pattern: STRATEGY PATTERN");
        System.out.println("• Lý do: Encapsulate các thuật toán, dễ swap strategy, tránh if-else\n");

        System.out.println("DEMO:");
        System.out.println("\n1. Sản phẩm với thuế VAT (10%):");
        Product product1 = new Product("P001", "Điện thoại", 1000);
        product1.setTaxStrategy(vatStrategy);
        System.out.println("   " + product1.getProductInfo());

        System.out.println("\n2. Sản phẩm với thuế tiêu thụ (5%):");
        Product product2 = new Product("P002", "Bia", 500);
        product2.setTaxStrategy(consumptionStrategy);
        System.out.println("   " + product2.getProductInfo());

        System.out.println("\n3. Sản phẩm với thuế xa xỉ (15%):");
        Product product3 = new Product("P003", "Đồng hồ Luxury", 10000);
        product3.setTaxStrategy(luxuryStrategy);
        System.out.println("   " + product3.getProductInfo());

        System.out.println("\n4. Thay đổi strategy động:");
        System.out.println("   Product 1 ban đầu dùng VAT:");
        System.out.println("   " + product1.getProductInfo());
        System.out.println("   Chuyển sang Luxury Tax:");
        product1.setTaxStrategy(luxuryStrategy);
        System.out.println("   " + product1.getProductInfo());
    }

    // ========== BÀI TOÁN 3: DECORATOR PATTERN ==========
    private void demonstrateDecoratorPattern() {
        System.out.println("\n┌─── BÀI TOÁN 3: DECORATOR PATTERN ───┐");
        System.out.println("│ Hệ thống thanh toán              │");
        System.out.println("└──────────────────────────────────┘\n");

        System.out.println("PHÂN TÍCH:");
        System.out.println("• Bài toán: Bọc thêm tính năng (Phí xử lý, Mã giảm giá) vào thanh toán gốc");
        System.out.println("• Pattern: DECORATOR PATTERN");
        System.out.println("• Lý do: Thêm responsibility động, tránh class explosion, compose linh hoạt\n");

        double amount = 1000;
        System.out.println("DEMO:");
        System.out.println("Số tiền gốc: " + amount);

        System.out.println("\n1. Thanh toán bằng Credit Card (không decorator):");
        PaymentMethod basicPayment = new CreditCardPayment();
        double cost1 = basicPayment.calculateAmount(amount);
        System.out.println("   Phương thức: " + basicPayment.getPaymentDetails());
        System.out.println("   Tổng tiền: " + String.format("%.2f", cost1));

        System.out.println("\n2. Thanh toán Credit Card + Phí xử lý (2%):");
        PaymentMethod decoratedPayment1 = new ProcessingFeeDecorator(new CreditCardPayment());
        double cost2 = decoratedPayment1.calculateAmount(amount);
        System.out.println("   Phương thức: " + decoratedPayment1.getPaymentDetails());
        System.out.println("   Tổng tiền: " + String.format("%.2f", cost2));

        System.out.println("\n3. Thanh toán Credit Card + Phí xử lý (2%) + Giảm giá (10%):");
        PaymentMethod decoratedPayment2 = new DiscountCodeDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment()),
            0.10
        );
        double cost3 = decoratedPayment2.calculateAmount(amount);
        System.out.println("   Phương thức: " + decoratedPayment2.getPaymentDetails());
        System.out.println("   Tổng tiền: " + String.format("%.2f", cost3));
        System.out.println("   Tiết kiệm so với cơ bản: " + String.format("%.2f", cost1 - cost3));
    }

    private void printConclusions() {
        System.out.println("\n1. DEPENDENCY INJECTION (DI):");
        System.out.println("   ✓ @Service, @Component, @Autowired quản lý lifecycle");
        System.out.println("   ✓ @Qualifier chọn implementation cụ thể");
        System.out.println("   ✓ Injection qua constructor hoặc field");

        System.out.println("\n2. INVERSION OF CONTROL (IoC):");
        System.out.println("   ✓ Spring Container tạo và quản lý object");
        System.out.println("   ✓ Code không cần 'new' object");
        System.out.println("   ✓ Thay đổi implementation mà không sửa code");

        System.out.println("\n3. FLEXIBILITY & EXTENSIBILITY:");
        System.out.println("   ✓ State Pattern: Thêm state mới chỉ cần @Component");
        System.out.println("   ✓ Strategy Pattern: Thay strategy qua @Qualifier");
        System.out.println("   ✓ Decorator Pattern: Compose decorator động");

        System.out.println("\n4. LOOSE COUPLING & TESTABILITY:");
        System.out.println("   ✓ Phụ thuộc vào interface, không concrete class");
        System.out.println("   ✓ Dễ mock khi test");
        System.out.println("   ✓ Giảm khớp nối giữa module");

        System.out.println("\n5. CONFIGURATION MANAGEMENT:");
        System.out.println("   ✓ Tập trung quản lý configuration");
        System.out.println("   ✓ Thay đổi behavior không cần recompile");
        System.out.println("   ✓ Dễ deploy ở các environment khác nhau");

        System.out.println("\n" + "=".repeat(80));
    }
}

