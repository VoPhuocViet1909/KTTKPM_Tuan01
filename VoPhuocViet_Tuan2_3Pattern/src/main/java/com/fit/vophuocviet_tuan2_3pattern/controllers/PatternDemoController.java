package com.fit.vophuocviet_tuan2_3pattern.controllers;

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
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller - Minh họa cách sử dụng 3 Design Pattern
 */
@RestController
@RequestMapping("/api/demo")
public class PatternDemoController {

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

    // ========== BÀI TOÁN 1: STATE PATTERN ==========
    @GetMapping("/state-pattern/order")
    public String demonstrateStatePattern() {
        StringBuilder response = new StringBuilder();
        response.append("<h2>STATE PATTERN - Hệ thống quản lý đơn hàng</h2>");
        response.append("<pre>");

        Order order = new Order("ORD-001");
        response.append("\n1. Tạo đơn hàng mới:\n");
        response.append("   Status: " + order.getStatus() + "\n\n");

        response.append("2. Xử lý đơn hàng (NEW -> PROCESSING):\n");
        orderService.processOrder(order);
        response.append("   Status: " + order.getStatus() + "\n\n");

        response.append("3. Giao đơn hàng (PROCESSING -> DELIVERED):\n");
        orderService.deliverOrder(order);
        response.append("   Status: " + order.getStatus() + "\n\n");

        response.append("4. Cố gắng hủy đơn hàng đã giao (không thể):\n");
        orderService.cancelOrder(order);
        response.append("   Status: " + order.getStatus() + "\n\n");

        response.append("LỢI ÍCH:\n");
        response.append("- Behavior thay đổi tùy state\n");
        response.append("- Không cần if-else dài\n");
        response.append("- Dễ mở rộng state mới\n");

        response.append("</pre>");
        return response.toString();
    }

    // ========== BÀI TOÁN 2: STRATEGY PATTERN ==========
    @GetMapping("/strategy-pattern/tax")
    public String demonstrateStrategyPattern() {
        StringBuilder response = new StringBuilder();
        response.append("<h2>STRATEGY PATTERN - Tính thuế sản phẩm</h2>");
        response.append("<pre>");

        response.append("\n1. Sản phẩm với thuế VAT (10%):\n");
        Product product1 = new Product("P001", "Điện thoại", 1000);
        product1.setTaxStrategy(vatStrategy);
        response.append("   " + product1.getProductInfo() + "\n\n");

        response.append("2. Sản phẩm với thuế tiêu thụ (5%):\n");
        Product product2 = new Product("P002", "Bia", 500);
        product2.setTaxStrategy(consumptionStrategy);
        response.append("   " + product2.getProductInfo() + "\n\n");

        response.append("3. Sản phẩm với thuế xa xỉ (15%):\n");
        Product product3 = new Product("P003", "Đồng hồ Luxury", 10000);
        product3.setTaxStrategy(luxuryStrategy);
        response.append("   " + product3.getProductInfo() + "\n\n");

        response.append("LỢI ÍCH:\n");
        response.append("- Thuật toán tính thuế linh hoạt\n");
        response.append("- Dễ thêm loại thuế mới\n");
        response.append("- Không phụ thuộc vào loại sản phẩm\n");

        response.append("</pre>");
        return response.toString();
    }

    // ========== BÀI TOÁN 3: DECORATOR PATTERN ==========
    @GetMapping("/decorator-pattern/payment")
    public String demonstrateDecoratorPattern() {
        StringBuilder response = new StringBuilder();
        response.append("<h2>DECORATOR PATTERN - Hệ thống thanh toán</h2>");
        response.append("<pre>");

        double amount = 1000;

        response.append("\n1. Thanh toán bằng Credit Card (không có decorator):\n");
        PaymentMethod basicPayment = new CreditCardPayment();
        double cost1 = basicPayment.calculateAmount(amount);
        response.append("   Phương thức: ").append(basicPayment.getPaymentDetails()).append("\n");
        response.append("   Tổng tiền: ").append(cost1).append("\n\n");

        response.append("2. Thanh toán bằng Credit Card + Phí xử lý (2%):\n");
        PaymentMethod decoratedPayment1 = new ProcessingFeeDecorator(new CreditCardPayment());
        double cost2 = decoratedPayment1.calculateAmount(amount);
        response.append("   Phương thức: ").append(decoratedPayment1.getPaymentDetails()).append("\n");
        response.append("   Tổng tiền: ").append(cost2).append("\n\n");

        response.append("3. Thanh toán Credit Card + Phí xử lý (2%) + Giảm giá (10%):\n");
        PaymentMethod decoratedPayment2 = new DiscountCodeDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment()),
            0.10
        );
        double cost3 = decoratedPayment2.calculateAmount(amount);
        response.append("   Phương thức: ").append(decoratedPayment2.getPaymentDetails()).append("\n");
        response.append("   Tổng tiền: ").append(cost3).append("\n\n");

        response.append("LỢI ÍCH:\n");
        response.append("- Thêm tính năng động mà không thay đổi class gốc\n");
        response.append("- Kết hợp nhiều decorator linh hoạt\n");
        response.append("- Tránh class explosion\n");

        response.append("</pre>");
        return response.toString();
    }

    // ========== TỔNG HỢP ==========
    @GetMapping("/all-patterns")
    public String demonstrateAllPatterns() {
        StringBuilder response = new StringBuilder();
        response.append("<h1>ỨNG DỤNG 3 DESIGN PATTERN TRONG SPRING BOOT</h1>");
        response.append("<ul>");
        response.append("<li><a href='/api/demo/state-pattern/order'>STATE PATTERN - Quản lý đơn hàng</a></li>");
        response.append("<li><a href='/api/demo/strategy-pattern/tax'>STRATEGY PATTERN - Tính thuế</a></li>");
        response.append("<li><a href='/api/demo/decorator-pattern/payment'>DECORATOR PATTERN - Thanh toán</a></li>");
        response.append("</ul>");
        response.append("<hr/>");
        response.append("<h2>KẾT LUẬN: Lợi ích kết hợp Pattern với DI của Spring Boot</h2>");
        response.append("<pre>");
        response.append("1. DEPENDENCY INJECTION (DI):\n");
        response.append("   - Các component được quản lý bởi Spring Container\n");
        response.append("   - @Autowired tự động inject dependency\n");
        response.append("   - @Qualifier giúp chọn implementation cụ thể\n\n");
        response.append("2. INVERSION OF CONTROL (IoC):\n");
        response.append("   - Spring tạo và quản lý lifecycle của object\n");
        response.append("   - Code không cần tạo object thủ công\n");
        response.append("   - Dễ thay đổi implementation mà không sửa code\n\n");
        response.append("3. FLEXIBILITY & EXTENSIBILITY:\n");
        response.append("   - State Pattern: Thêm state mới chỉ cần @Component\n");
        response.append("   - Strategy Pattern: Thay đổi strategy qua @Qualifier\n");
        response.append("   - Decorator Pattern: Compose decorator động\n\n");
        response.append("4. LOOSE COUPLING & TESTABILITY:\n");
        response.append("   - Code không phụ thuộc vào concrete class\n");
        response.append("   - Dễ mock khi test\n");
        response.append("   - Thay đổi implementation không ảnh hưởng client\n\n");
        response.append("5. CONFIGURATION MANAGEMENT:\n");
        response.append("   - Tập trung quản lý configuration\n");
        response.append("   - Application properties dễ thay đổi\n");
        response.append("   - Không cần recompile khi thay đổi behavior\n");
        response.append("</pre>");
        return response.toString();
    }
}

