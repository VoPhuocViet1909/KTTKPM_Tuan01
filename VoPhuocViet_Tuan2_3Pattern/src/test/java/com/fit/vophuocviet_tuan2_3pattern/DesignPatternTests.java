package com.fit.vophuocviet_tuan2_3pattern;

import com.fit.vophuocviet_tuan2_3pattern.problem1_state.Order;
import com.fit.vophuocviet_tuan2_3pattern.problem1_state.OrderService;
import com.fit.vophuocviet_tuan2_3pattern.problem2_strategy.Product;
import com.fit.vophuocviet_tuan2_3pattern.problem2_strategy.TaxStrategy;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.CreditCardPayment;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.DiscountCodeDecorator;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.PaymentMethod;
import com.fit.vophuocviet_tuan2_3pattern.problem3_decorator.ProcessingFeeDecorator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class cho 3 Design Pattern
 * Sử dụng Spring Boot Test Framework
 */
@SpringBootTest
@DisplayName("Design Pattern Integration Tests")
public class DesignPatternTests {

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

    // ========== STATE PATTERN TESTS ==========

    @Test
    @DisplayName("Test State Pattern: Order lifecycle NEW -> PROCESSING -> DELIVERED")
    public void testOrderStateTransition() {
        // Arrange
        Order order = new Order("ORD-001");

        // Act & Assert - Step 1: Initial state is NEW
        assertEquals("NEW", order.getStatus(), "Order should start in NEW state");

        // Act & Assert - Step 2: Process order (NEW -> PROCESSING)
        orderService.processOrder(order);
        assertEquals("PROCESSING", order.getStatus(), "Order should be in PROCESSING after process()");

        // Act & Assert - Step 3: Deliver order (PROCESSING -> DELIVERED)
        orderService.deliverOrder(order);
        assertEquals("DELIVERED", order.getStatus(), "Order should be in DELIVERED after deliver()");
    }

    @Test
    @DisplayName("Test State Pattern: Cannot deliver from NEW state")
    public void testCannotDeliverFromNewState() {
        // Arrange
        Order order = new Order("ORD-002");
        assertEquals("NEW", order.getStatus());

        // Act & Assert
        assertDoesNotThrow(() -> {
            orderService.deliverOrder(order);
        });
        // Order state should remain NEW (không thể chuyển sang DELIVERED từ NEW)
        assertEquals("NEW", order.getStatus(), "Order should remain in NEW state");
    }

    @Test
    @DisplayName("Test State Pattern: Can cancel from NEW state")
    public void testCanCancelFromNewState() {
        // Arrange
        Order order = new Order("ORD-003");
        assertEquals("NEW", order.getStatus());

        // Act
        orderService.cancelOrder(order);

        // Assert
        assertEquals("CANCELLED", order.getStatus(), "Order should be in CANCELLED state");
    }

    @Test
    @DisplayName("Test State Pattern: Cannot cancel from DELIVERED state")
    public void testCannotCancelFromDeliveredState() {
        // Arrange
        Order order = new Order("ORD-004");
        orderService.processOrder(order);
        orderService.deliverOrder(order);
        assertEquals("DELIVERED", order.getStatus());

        // Act
        orderService.cancelOrder(order);

        // Assert
        assertEquals("DELIVERED", order.getStatus(), "Order should remain in DELIVERED state");
    }

    // ========== STRATEGY PATTERN TESTS ==========

    @Test
    @DisplayName("Test Strategy Pattern: VAT Tax (10%)")
    public void testVATTaxStrategy() {
        // Arrange
        double price = 1000;
        double expectedTax = 100; // 10%

        // Act
        double actualTax = vatStrategy.calculateTax(price);

        // Assert
        assertEquals(expectedTax, actualTax, 0.01, "VAT should be 10% of price");
    }

    @Test
    @DisplayName("Test Strategy Pattern: Consumption Tax (5%)")
    public void testConsumptionTaxStrategy() {
        // Arrange
        double price = 1000;
        double expectedTax = 50; // 5%

        // Act
        double actualTax = consumptionStrategy.calculateTax(price);

        // Assert
        assertEquals(expectedTax, actualTax, 0.01, "Consumption tax should be 5% of price");
    }

    @Test
    @DisplayName("Test Strategy Pattern: Luxury Tax (15%)")
    public void testLuxuryTaxStrategy() {
        // Arrange
        double price = 1000;
        double expectedTax = 150; // 15%

        // Act
        double actualTax = luxuryStrategy.calculateTax(price);

        // Assert
        assertEquals(expectedTax, actualTax, 0.01, "Luxury tax should be 15% of price");
    }

    @Test
    @DisplayName("Test Strategy Pattern: Product with dynamic tax change")
    public void testProductWithDynamicTaxChange() {
        // Arrange
        Product product = new Product("P001", "Watch", 1000);

        // Act & Assert - Set VAT
        product.setTaxStrategy(vatStrategy);
        assertEquals(1100, product.calculateTotalPrice(), 0.01, "Total with VAT should be 1100");

        // Act & Assert - Change to Luxury Tax
        product.setTaxStrategy(luxuryStrategy);
        assertEquals(1150, product.calculateTotalPrice(), 0.01, "Total with Luxury Tax should be 1150");
    }

    // ========== DECORATOR PATTERN TESTS ==========

    @Test
    @DisplayName("Test Decorator Pattern: Basic Credit Card payment")
    public void testBasicCreditCardPayment() {
        // Arrange
        double amount = 1000;
        PaymentMethod payment = new CreditCardPayment();

        // Act
        double total = payment.calculateAmount(amount);

        // Assert
        assertEquals(1000, total, 0.01, "Basic payment should be 1000");
        assertEquals("Credit Card Payment", payment.getPaymentDetails());
    }

    @Test
    @DisplayName("Test Decorator Pattern: Credit Card with Processing Fee")
    public void testCreditCardWithProcessingFee() {
        // Arrange
        double amount = 1000;
        PaymentMethod payment = new ProcessingFeeDecorator(new CreditCardPayment());

        // Act
        double total = payment.calculateAmount(amount);

        // Assert
        double expectedFee = 1000 * 0.02; // 2% fee
        double expectedTotal = 1000 + expectedFee; // 1020
        assertEquals(expectedTotal, total, 0.01, "Total should include 2% processing fee");
        assertTrue(payment.getPaymentDetails().contains("Processing Fee"));
    }

    @Test
    @DisplayName("Test Decorator Pattern: Credit Card with Processing Fee + Discount")
    public void testCreditCardWithFeeAndDiscount() {
        // Arrange
        double amount = 1000;
        PaymentMethod payment = new DiscountCodeDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment()),
            0.10 // 10% discount
        );

        // Act
        double total = payment.calculateAmount(amount);

        // Assert
        double afterFee = 1000 + (1000 * 0.02);      // 1020
        double afterDiscount = afterFee - (afterFee * 0.10); // 918
        assertEquals(afterDiscount, total, 0.01, "Total should apply fee then discount");
        assertTrue(payment.getPaymentDetails().contains("Processing Fee"));
        assertTrue(payment.getPaymentDetails().contains("Discount Code"));
    }

    @Test
    @DisplayName("Test Decorator Pattern: Multiple decorator combination")
    public void testMultipleDecoratorCombination() {
        // Arrange
        double amount = 500;

        // Act - Without any decorator
        PaymentMethod basic = new CreditCardPayment();
        double cost1 = basic.calculateAmount(amount);

        // Act - With processing fee only
        PaymentMethod withFee = new ProcessingFeeDecorator(new CreditCardPayment());
        double cost2 = withFee.calculateAmount(amount);

        // Act - With both fee and discount
        PaymentMethod withBoth = new DiscountCodeDecorator(
            new ProcessingFeeDecorator(new CreditCardPayment()),
            0.05 // 5% discount
        );
        double cost3 = withBoth.calculateAmount(amount);

        // Assert
        assertEquals(500, cost1, 0.01);
        assertTrue(cost2 > cost1, "Cost with fee should be greater");
        assertTrue(cost3 > cost1 && cost3 < cost2, "Cost with fee and discount should be between");
    }

    // ========== INTEGRATION TESTS ==========

    @Test
    @DisplayName("Integration Test: Complete workflow")
    public void testCompleteWorkflow() {
        // 1. Create and process an order
        Order order = new Order("ORD-COMPLETE");
        orderService.processOrder(order);
        assertEquals("PROCESSING", order.getStatus());

        // 2. Calculate tax for a product
        Product product = new Product("P-COMPLETE", "Item", 1000);
        product.setTaxStrategy(vatStrategy);
        double totalPrice = product.calculateTotalPrice();
        assertEquals(1100, totalPrice, 0.01);

        // 3. Process payment with decorator
        PaymentMethod payment = new ProcessingFeeDecorator(new CreditCardPayment());
        double paymentAmount = payment.calculateAmount(totalPrice);
        assertTrue(paymentAmount > totalPrice);

        // Assert everything works together
        assertTrue(order.getOrderId().equals("ORD-COMPLETE"));
        assertTrue(product.getPrice() == 1000);
        assertTrue(paymentAmount > 0);
    }
}

