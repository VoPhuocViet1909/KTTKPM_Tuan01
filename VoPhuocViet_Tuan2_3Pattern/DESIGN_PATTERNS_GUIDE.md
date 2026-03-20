# 3 DESIGN PATTERN TRONG SPRING BOOT

## 📋 MỤC LỤC
1. BÀI TOÁN 1: STATE PATTERN - Hệ thống quản lý đơn hàng
2. BÀI TOÁN 2: STRATEGY PATTERN - Tính thuế sản phẩm
3. BÀI TOÁN 3: DECORATOR PATTERN - Hệ thống thanh toán
4. Kết luận: Lợi ích kết hợp Pattern với DI của Spring Boot

---

## 🎯 BÀI TOÁN 1: STATE PATTERN

### 📌 Phân tích bài toán
**Yêu cầu:** Xây dựng hệ thống quản lý đơn hàng với các trạng thái khác nhau:
- **NEW**: Đơn hàng vừa tạo
- **PROCESSING**: Đang xử lý
- **DELIVERED**: Đã giao
- **CANCELLED**: Đã hủy

**Vấn đề:** Hành vi của Order thay đổi tùy vào trạng thái hiện tại
- Nếu NEW: có thể process hoặc cancel
- Nếu PROCESSING: có thể deliver hoặc cancel
- Nếu DELIVERED: không thể làm gì
- Nếu CANCELLED: không thể làm gì

### ✅ Lý do chọn STATE PATTERN
1. **Behavior thay đổi theo state** → Mỗi state có cách xử lý riêng
2. **Tránh if-else dài** → Không cần kiểm tra state trong mỗi method
3. **Dễ mở rộng** → Thêm state mới chỉ cần tạo class mới implement OrderState
4. **Encapsulation** → Logic của mỗi state được đóng gói riêng

### 🏗️ Cấu trúc Code

**Interface - OrderState.java**
```java
public interface OrderState {
    void process(Order order);
    void deliver(Order order);
    void cancel(Order order);
    String getStatus();
}
```

**Concrete States** (NewOrderState, ProcessingOrderState, DeliveredOrderState, CancelledOrderState)
```java
@Component
public class NewOrderState implements OrderState {
    @Override
    public void process(Order order) {
        System.out.println("[NEW STATE] Xử lý đơn hàng...");
        order.setState(new ProcessingOrderState());
    }
    // ...
}
```

**Context - Order.java**
```java
public class Order {
    private OrderState state;
    
    public Order(String orderId) {
        this.state = new NewOrderState(); // State mặc định
    }
    
    public void process() {
        state.process(this);
    }
    
    public void setState(OrderState state) {
        this.state = state;
    }
}
```

**Service - OrderService.java**
```java
@Service
public class OrderService {
    public void processOrder(Order order) {
        order.process();
    }
    
    public void deliverOrder(Order order) {
        order.deliver();
    }
    
    public void cancelOrder(Order order) {
        order.cancel();
    }
}
```

### 📊 Flow Demo
```
Order: ORD-001
│
├─ NEW STATE (Trạng thái ban đầu)
│  ├─ process() → Chuyển sang PROCESSING
│  ├─ cancel() → Chuyển sang CANCELLED
│  └─ deliver() → Lỗi (không thể giao từ NEW)
│
├─ PROCESSING STATE
│  ├─ deliver() → Chuyển sang DELIVERED
│  ├─ cancel() → Chuyển sang CANCELLED
│  └─ process() → Lỗi (đã đang xử lý)
│
├─ DELIVERED STATE
│  ├─ cancel() → Lỗi (đã giao không thể hủy)
│  ├─ deliver() → Lỗi (đã giao rồi)
│  └─ process() → Lỗi (đã giao rồi)
│
└─ CANCELLED STATE
   ├─ Không thể làm gì cả
```

### 💡 Lợi ích
- ✓ Code dễ đọc, logic rõ ràng
- ✓ Dễ thêm state mới
- ✓ Không có if-else phức tạp
- ✓ Mỗi state độc lập, dễ test

---

## 🎯 BÀI TOÁN 2: STRATEGY PATTERN

### 📌 Phân tích bài toán
**Yêu cầu:** Tính toán thuế cho sản phẩm với các loại thuế khác nhau:
- **VAT (Giá trị gia tăng)**: 10%
- **Consumption Tax (Tiêu thụ)**: 5%
- **Luxury Tax (Xa xỉ)**: 15%

**Vấn đề:** Thuật toán tính thuế thay đổi linh hoạt tùy loại sản phẩm
- Điện thoại → VAT (10%)
- Bia → Consumption Tax (5%)
- Đồng hồ Luxury → Luxury Tax (15%)

### ✅ Lý do chọn STRATEGY PATTERN
1. **Encapsulate các thuật toán** → Mỗi thuế có class riêng
2. **Dễ swap strategy** → Thay đổi loại thuế trong runtime
3. **Tránh if-else** → Không cần kiểm tra loại sản phẩm
4. **Open/Closed Principle** → Mở cho extension, đóng cho modification
5. **Reusability** → Tái sử dụng strategy cho nhiều sản phẩm

### 🏗️ Cấu trúc Code

**Interface - TaxStrategy.java**
```java
public interface TaxStrategy {
    double calculateTax(double price);
    String getTaxName();
}
```

**Concrete Strategies**
```java
@Component("vatTaxStrategy")
public class VATTaxStrategy implements TaxStrategy {
    private static final double VAT_RATE = 0.10;
    
    @Override
    public double calculateTax(double price) {
        return price * VAT_RATE;
    }
    
    @Override
    public String getTaxName() {
        return "VAT (10%)";
    }
}

@Component("luxuryTaxStrategy")
public class LuxuryTaxStrategy implements TaxStrategy {
    private static final double LUXURY_TAX_RATE = 0.15;
    // ...
}
```

**Context - Product.java**
```java
public class Product {
    private double price;
    private TaxStrategy taxStrategy;
    
    public void setTaxStrategy(TaxStrategy taxStrategy) {
        this.taxStrategy = taxStrategy;
    }
    
    public double calculateTotalPrice() {
        double tax = taxStrategy.calculateTax(price);
        return price + tax;
    }
}
```

### 📊 Flow Demo
```
Product: Điện thoại (Giá: 1000)
├─ Gán Strategy: VATTaxStrategy (10%)
├─ Tính thuế: 1000 * 0.10 = 100
└─ Tổng: 1000 + 100 = 1100

Product: Bia (Giá: 500)
├─ Gán Strategy: ConsumptionTaxStrategy (5%)
├─ Tính thuế: 500 * 0.05 = 25
└─ Tổng: 500 + 25 = 525

Product: Đồng hồ Luxury (Giá: 10000)
├─ Gán Strategy: LuxuryTaxStrategy (15%)
├─ Tính thuế: 10000 * 0.15 = 1500
└─ Tổng: 10000 + 1500 = 11500
```

### 💡 Lợi ích
- ✓ Dễ thêm loại thuế mới
- ✓ Không phụ thuộc vào Product class
- ✓ Strategy có thể thay đổi runtime
- ✓ Dễ test từng strategy riêng biệt

---

## 🎯 BÀI TOÁN 3: DECORATOR PATTERN

### 📌 Phân tích bài toán
**Yêu cầu:** Hệ thống thanh toán với các phương thức khác nhau:
- **Credit Card**: Thanh toán bằng thẻ tín dụng
- **PayPal**: Thanh toán qua PayPal

**Thêm tính năng:**
- **Processing Fee**: Phí xử lý (2%)
- **Discount Code**: Mã giảm giá

**Vấn đề:** Cần bọc thêm tính năng vào thanh toán gốc mà không thay đổi class gốc

### ✅ Lý do chọn DECORATOR PATTERN
1. **Thêm responsibility động** → Thêm fee, discount mà không sửa class gốc
2. **Tránh class explosion** → Không cần CreditCardWithFeeAndDiscount
3. **Flexible composition** → Có thể kết hợp nhiều decorator
4. **Open/Closed Principle** → Mở cho extension, đóng cho modification
5. **Single Responsibility** → Mỗi decorator có 1 trách nhiệm

### 🏗️ Cấu trúc Code

**Interface - PaymentMethod.java**
```java
public interface PaymentMethod {
    double calculateAmount(double amount);
    String getPaymentDetails();
}
```

**Concrete Component - CreditCardPayment.java**
```java
@Component
public class CreditCardPayment implements PaymentMethod {
    @Override
    public double calculateAmount(double amount) {
        System.out.println("[CREDIT CARD] Thanh toán: " + amount);
        return amount;
    }
    
    @Override
    public String getPaymentDetails() {
        return "Credit Card Payment";
    }
}
```

**Abstract Decorator - PaymentDecorator.java**
```java
public abstract class PaymentDecorator implements PaymentMethod {
    protected PaymentMethod wrappedPayment;
    
    public PaymentDecorator(PaymentMethod wrappedPayment) {
        this.wrappedPayment = wrappedPayment;
    }
    
    @Override
    public double calculateAmount(double amount) {
        return wrappedPayment.calculateAmount(amount);
    }
    
    @Override
    public String getPaymentDetails() {
        return wrappedPayment.getPaymentDetails();
    }
}
```

**Concrete Decorator - ProcessingFeeDecorator.java**
```java
@Component
public class ProcessingFeeDecorator extends PaymentDecorator {
    private static final double PROCESSING_FEE_RATE = 0.02;
    
    public ProcessingFeeDecorator(PaymentMethod wrappedPayment) {
        super(wrappedPayment);
    }
    
    @Override
    public double calculateAmount(double amount) {
        double baseAmount = wrappedPayment.calculateAmount(amount);
        double fee = baseAmount * PROCESSING_FEE_RATE;
        System.out.println("[PROCESSING FEE] Phí xử lý (2%): " + fee);
        return baseAmount + fee;
    }
    
    @Override
    public String getPaymentDetails() {
        return wrappedPayment.getPaymentDetails() + " + Processing Fee (2%)";
    }
}
```

### 📊 Flow Demo
```
Số tiền gốc: 1000

1. Thanh toán Credit Card
   └─ CreditCardPayment
      └─ Tổng: 1000

2. Thanh toán Credit Card + Processing Fee (2%)
   └─ ProcessingFeeDecorator
      └─ CreditCardPayment
         ├─ Base: 1000
         ├─ Fee (2%): 20
         └─ Tổng: 1020

3. Thanh toán Credit Card + Processing Fee + Discount (nếu có thêm)
   └─ DiscountDecorator
      └─ ProcessingFeeDecorator
         └─ CreditCardPayment
            └─ Tổng: 1020 - discount
```

### 💡 Lợi ích
- ✓ Thêm tính năng không sửa class gốc
- ✓ Kết hợp decorator linh hoạt
- ✓ Không bị class explosion
- ✓ Dễ test từng decorator riêng biệt

---

## 🔗 Tích hợp với Spring Boot DI/IoC

### Cách sử dụng @Autowired & @Qualifier

**Trong DesignPatternDemo.java:**
```java
@Component
public class DesignPatternDemo implements CommandLineRunner {
    
    @Autowired
    private OrderService orderService; // Inject OrderService
    
    @Autowired
    @Qualifier("vatTaxStrategy")
    private TaxStrategy vatStrategy; // Chọn VATTaxStrategy cụ thể
    
    @Autowired
    @Qualifier("luxuryTaxStrategy")
    private TaxStrategy luxuryStrategy; // Chọn LuxuryTaxStrategy cụ thể
    
    @Override
    public void run(String... args) {
        // Spring Boot gọi method này khi ứng dụng khởi động
        demonstrateStatePattern();
        demonstrateStrategyPattern();
        demonstrateDecoratorPattern();
    }
}
```

### Cách sử dụng trong REST Controller

**PatternDemoController.java:**
```java
@RestController
@RequestMapping("/api/demo")
public class PatternDemoController {
    
    @Autowired
    private OrderService orderService;
    
    @Autowired
    @Qualifier("vatTaxStrategy")
    private TaxStrategy vatStrategy;
    
    @GetMapping("/state-pattern/order")
    public String demonstrateStatePattern() {
        // Sử dụng các pattern trong API
    }
    
    @GetMapping("/strategy-pattern/tax")
    public String demonstrateStrategyPattern() {
        // Sử dụng Strategy Pattern
    }
    
    @GetMapping("/decorator-pattern/payment")
    public String demonstrateDecoratorPattern() {
        // Sử dụng Decorator Pattern
    }
}
```

---

## 🎓 KẾT LUẬN: Lợi ích kết hợp Pattern với DI của Spring Boot

### 1. **DEPENDENCY INJECTION (DI)**
```
✓ @Service, @Component, @Autowired quản lý lifecycle
✓ @Qualifier giúp chọn implementation cụ thể
✓ Injection qua field hoặc constructor
✓ Không cần tạo object thủ công (new)
```

**Ví dụ:**
```java
@Service
public class OrderService {
    // Spring tự động tạo instance
}

@Component
public class OrderStateImpl implements OrderState {
    // Spring tự động quản lý
}
```

### 2. **INVERSION OF CONTROL (IoC)**
```
✓ Spring Container tạo và quản lý object
✓ Code không cần 'new' object
✓ Thay đổi implementation mà không sửa code
✓ Dependency được inject tự động
```

**Ví dụ:**
```java
// Thay vì:
OrderService service = new OrderService();

// Sử dụng:
@Autowired
OrderService service; // Spring tự inject
```

### 3. **FLEXIBILITY & EXTENSIBILITY**
```
✓ State Pattern: Thêm state mới chỉ cần @Component
✓ Strategy Pattern: Thay strategy qua @Qualifier
✓ Decorator Pattern: Compose decorator động
✓ Không cần sửa class Context
```

**Ví dụ thêm state mới (không sửa Order.java):**
```java
@Component
public class RefundingOrderState implements OrderState {
    // Thêm state mới chỉ cần 1 class
}
```

### 4. **LOOSE COUPLING & TESTABILITY**
```
✓ Code phụ thuộc vào interface, không concrete class
✓ Dễ mock khi test
✓ Giảm khớp nối giữa module
✓ Dễ thay đổi behavior
```

**Ví dụ test:**
```java
@Test
public void testOrderStateTransition() {
    OrderState mockState = mock(OrderState.class);
    Order order = new Order("ORD-001");
    order.setState(mockState);
    // Test dễ dàng
}
```

### 5. **CONFIGURATION MANAGEMENT**
```
✓ Tập trung quản lý configuration
✓ Thay đổi behavior không cần recompile
✓ Dễ deploy ở các environment khác nhau
✓ Application properties dễ quản lý
```

---

## 📁 Cấu trúc Project

```
src/main/java/com/fit/vophuocviet_tuan2_3pattern/
├── DesignPatternDemo.java                    (CommandLineRunner)
├── VoPhuocVietTuan23PatternApplication.java  (Main Entry Point)
├── controllers/
│   └── PatternDemoController.java            (REST API)
├── problem1_state/
│   ├── OrderState.java                       (Interface)
│   ├── Order.java                            (Context)
│   ├── OrderService.java                     (Service)
│   ├── NewOrderState.java                    (Concrete State)
│   ├── ProcessingOrderState.java
│   ├── DeliveredOrderState.java
│   └── CancelledOrderState.java
├── problem2_strategy/
│   ├── TaxStrategy.java                      (Interface)
│   ├── Product.java                          (Context)
│   ├── VATTaxStrategy.java                   (Concrete Strategy)
│   ├── ConsumptionTaxStrategy.java
│   └── LuxuryTaxStrategy.java
└── problem3_decorator/
    ├── PaymentMethod.java                    (Interface)
    ├── PaymentDecorator.java                 (Abstract Decorator)
    ├── CreditCardPayment.java                (Concrete Component)
    └── ProcessingFeeDecorator.java           (Concrete Decorator)
```

---

## 🚀 Cách chạy ứng dụng

### 1. Chạy Spring Boot Application
```bash
mvn spring-boot:run
```

**Output tại console:**
```
================================================================================
DEMO: 3 DESIGN PATTERN TRONG SPRING BOOT
================================================================================

┌─── BÀI TOÁN 1: STATE PATTERN ───┐
│ Hệ thống quản lý đơn hàng        │
└──────────────────────────────────┘
...
```

### 2. Truy cập REST API
```
http://localhost:8080/api/demo/all-patterns
http://localhost:8080/api/demo/state-pattern/order
http://localhost:8080/api/demo/strategy-pattern/tax
http://localhost:8080/api/demo/decorator-pattern/payment
```

### 3. Xem log trong IDE
- Visual Studio Code: View → Output
- IntelliJ IDEA: View → Tool Windows → Run
- Eclipse: Window → Show View → Console

---

## 📚 Tài liệu tham khảo

### Design Patterns
- **Gang of Four (GoF)** - Design Patterns: Elements of Reusable Object-Oriented Software
- State Pattern: https://refactoring.guru/design-patterns/state
- Strategy Pattern: https://refactoring.guru/design-patterns/strategy
- Decorator Pattern: https://refactoring.guru/design-patterns/decorator

### Spring Boot
- Spring Framework Documentation: https://spring.io/projects/spring-framework
- Spring Boot Reference Guide: https://spring.io/projects/spring-boot
- Dependency Injection in Spring: https://spring.io/guides/gs/dependency-injection/

---

## ✨ Tóm tắt
- **STATE PATTERN**: Quản lý trạng thái + hành vi của đối tượng
- **STRATEGY PATTERN**: Encapsulate các thuật toán có thể thay đổi
- **DECORATOR PATTERN**: Thêm tính năng động mà không sửa class gốc
- **Spring DI/IoC**: Quản lý dependency tự động + tính reusability cao

