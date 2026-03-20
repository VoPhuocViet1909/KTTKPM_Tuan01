# 3 DESIGN PATTERNS TRONG SPRING BOOT 4.0.4

## 📝 Giới thiệu
Dự án này triển khai **3 Design Pattern** (State, Strategy, Decorator) trong môi trường **Spring Boot** với **Dependency Injection (DI)** và **Inversion of Control (IoC)**.

### 🎯 Mục tiêu
- ✅ Giải quyết 3 bài toán thực tế bằng Design Pattern
- ✅ Áp dụng DI/IoC của Spring Boot (@Service, @Component, @Autowired)
- ✅ Tránh cấu trúc main() console thông thường
- ✅ Cung cấp REST API để minh họa các Pattern

---

## 📦 Cấu trúc Dự án

```
VoPhuocViet_Tuan2_3Pattern/
│
├── pom.xml                               # Maven configuration
├── DESIGN_PATTERNS_GUIDE.md              # Tài liệu chi tiết
├── README.md                             # File này
│
├── src/main/java/com/fit/vophuocviet_tuan2_3pattern/
│   │
│   ├── VoPhuocVietTuan23PatternApplication.java   # Entry point
│   ├── DesignPatternDemo.java                     # CommandLineRunner (thay thế main())
│   │
│   ├── controllers/
│   │   └── PatternDemoController.java             # REST API endpoints
│   │
│   ├── problem1_state/                            # BÀI TOÁN 1: STATE PATTERN
│   │   ├── OrderState.java                        # Interface
│   │   ├── Order.java                             # Context
│   │   ├── OrderService.java                      # Service
│   │   ├── NewOrderState.java                     # Concrete State
│   │   ├── ProcessingOrderState.java
│   │   ├── DeliveredOrderState.java
│   │   └── CancelledOrderState.java
│   │
│   ├── problem2_strategy/                         # BÀI TOÁN 2: STRATEGY PATTERN
│   │   ├── TaxStrategy.java                       # Interface
│   │   ├── Product.java                           # Context
│   │   ├── VATTaxStrategy.java                    # Concrete Strategy
│   │   ├── ConsumptionTaxStrategy.java
│   │   └── LuxuryTaxStrategy.java
│   │
│   └── problem3_decorator/                        # BÀI TOÁN 3: DECORATOR PATTERN
│       ├── PaymentMethod.java                     # Interface
│       ├── PaymentDecorator.java                  # Abstract Decorator
│       ├── CreditCardPayment.java                 # Concrete Component
│       ├── ProcessingFeeDecorator.java            # Concrete Decorator
│       └── DiscountCodeDecorator.java             # Concrete Decorator
│
└── src/resources/
    └── application.properties
```

---

## 🚀 Cách chạy ứng dụng

### 1. **Yêu cầu hệ thống**
- Java 17+
- Maven 3.6+
- Spring Boot 4.0.4

### 2. **Compile project**
```bash
cd VoPhuocViet_Tuan2_3Pattern
mvnw clean compile
```

### 3. **Chạy Spring Boot Application**
```bash
mvnw spring-boot:run
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

### 4. **Truy cập REST API**
Ứng dụng sẽ chạy tại `http://localhost:8080`

**Các endpoint có sẵn:**
```
GET /api/demo/all-patterns                  # Trang chủ với tất cả pattern
GET /api/demo/state-pattern/order           # Demo State Pattern
GET /api/demo/strategy-pattern/tax          # Demo Strategy Pattern
GET /api/demo/decorator-pattern/payment     # Demo Decorator Pattern
```

---

## 📚 Chi tiết 3 Design Pattern

### 🔵 **BÀI TOÁN 1: STATE PATTERN**

**Vấn đề:**
- Hệ thống quản lý Order với các trạng thái: NEW, PROCESSING, DELIVERED, CANCELLED
- Hành vi thay đổi tùy vào trạng thái hiện tại

**Giải pháp:**
- Interface `OrderState` định nghĩa các hành động
- Mỗi state là 1 class implement `OrderState`
- `Order` class giữ reference đến state hiện tại

**Code Demo:**
```java
Order order = new Order("ORD-001");
orderService.processOrder(order);    // NEW -> PROCESSING
orderService.deliverOrder(order);    // PROCESSING -> DELIVERED
orderService.cancelOrder(order);     // Lỗi: không thể hủy DELIVERED
```

**Lợi ích:**
- ✓ Behavior rõ ràng theo state
- ✓ Tránh if-else phức tạp
- ✓ Dễ thêm state mới

---

### 🟢 **BÀI TOÁN 2: STRATEGY PATTERN**

**Vấn đề:**
- Tính toán thuế cho sản phẩm với nhiều loại thuế: VAT (10%), Tiêu thụ (5%), Xa xỉ (15%)
- Thuật toán thay đổi tùy loại sản phẩm

**Giải pháp:**
- Interface `TaxStrategy` định nghĩa cách tính thuế
- Mỗi loại thuế là 1 class implement `TaxStrategy`
- `Product` class sử dụng strategy được inject

**Code Demo:**
```java
Product phone = new Product("P001", "Điện thoại", 1000);
phone.setTaxStrategy(vatStrategy);           // 10% VAT
double total = phone.calculateTotalPrice();  // 1100

Product watch = new Product("P002", "Đồng hồ", 10000);
watch.setTaxStrategy(luxuryStrategy);        // 15% Luxury Tax
double total = watch.calculateTotalPrice();  // 11500
```

**Lợi ích:**
- ✓ Dễ thêm loại thuế mới
- ✓ Strategy có thể thay đổi runtime
- ✓ Không phụ thuộc vào Product

---

### 🔴 **BÀI TOÁN 3: DECORATOR PATTERN**

**Vấn đề:**
- Hệ thống thanh toán (Credit Card, PayPal)
- Thêm tính năng: phí xử lý, mã giảm giá vào thanh toán gốc
- Tránh tạo class cho mỗi kết hợp (CreditCardWithFeeAndDiscount, ...)

**Giải pháp:**
- Interface `PaymentMethod` cơ bản
- `PaymentDecorator` abstract class bọc `PaymentMethod`
- Mỗi decorator thêm 1 tính năng mới

**Code Demo:**
```java
double amount = 1000;

// Thanh toán cơ bản: Credit Card
PaymentMethod payment1 = new CreditCardPayment();
double cost1 = payment1.calculateAmount(amount);  // 1000

// Thanh toán + Phí xử lý (2%)
PaymentMethod payment2 = new ProcessingFeeDecorator(
    new CreditCardPayment()
);
double cost2 = payment2.calculateAmount(amount);  // 1020

// Thanh toán + Phí xử lý (2%) + Giảm giá (10%)
PaymentMethod payment3 = new DiscountCodeDecorator(
    new ProcessingFeeDecorator(new CreditCardPayment()),
    0.10
);
double cost3 = payment3.calculateAmount(amount);  // 918 (1020 - 102)
```

**Lợi ích:**
- ✓ Thêm tính năng không sửa class gốc
- ✓ Kết hợp decorator linh hoạt
- ✓ Tránh class explosion

---

## 🔌 Tích hợp với Spring Boot DI/IoC

### **Ví dụ 1: @Autowired + @Qualifier**
```java
@Component
public class DesignPatternDemo implements CommandLineRunner {
    
    @Autowired
    private OrderService orderService;  // Inject OrderService
    
    @Autowired
    @Qualifier("vatTaxStrategy")
    private TaxStrategy vatStrategy;    // Chọn implementation cụ thể
    
    @Override
    public void run(String... args) {
        // Spring Boot tự động gọi method này khi khởi động
        demonstrateStatePattern();
        demonstrateStrategyPattern();
    }
}
```

### **Ví dụ 2: Constructor Injection**
```java
@Service
public class OrderService {
    private final OrderRepository repository;
    
    // Constructor injection
    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }
}
```

### **Ví dụ 3: REST Controller**
```java
@RestController
@RequestMapping("/api/demo")
public class PatternDemoController {
    
    @Autowired
    private OrderService orderService;
    
    @GetMapping("/state-pattern/order")
    public String demonstrateStatePattern() {
        Order order = new Order("ORD-001");
        orderService.processOrder(order);
        return "...";
    }
}
```

---

## 🎓 Lợi ích kết hợp Pattern với Spring DI/IoC

### 1️⃣ **DEPENDENCY INJECTION (DI)**
```
✓ @Service, @Component, @Autowired quản lý lifecycle
✓ @Qualifier giúp chọn implementation cụ thể
✓ Injection qua field hoặc constructor
✓ Không cần tạo object thủ công (new)
```

### 2️⃣ **INVERSION OF CONTROL (IoC)**
```
✓ Spring Container tạo và quản lý object
✓ Code không cần 'new' object
✓ Thay đổi implementation mà không sửa code
✓ Dependency được inject tự động
```

### 3️⃣ **FLEXIBILITY & EXTENSIBILITY**
```
✓ State Pattern: Thêm state mới chỉ cần @Component
✓ Strategy Pattern: Thay strategy qua @Qualifier
✓ Decorator Pattern: Compose decorator động
✓ Không cần sửa code cũ
```

### 4️⃣ **LOOSE COUPLING & TESTABILITY**
```
✓ Code phụ thuộc vào interface, không concrete class
✓ Dễ mock khi test
✓ Giảm khớp nối giữa module
✓ Dễ thay đổi behavior
```

### 5️⃣ **CONFIGURATION MANAGEMENT**
```
✓ Tập trung quản lý configuration
✓ Thay đổi behavior không cần recompile
✓ Dễ deploy ở các environment khác nhau
✓ Application properties dễ quản lý
```

---

## 🧪 Unit Testing Example

```java
@SpringBootTest
public class OrderServiceTest {
    
    @Autowired
    private OrderService orderService;
    
    @Test
    public void testOrderStateTransition() {
        Order order = new Order("ORD-001");
        assertEquals("NEW", order.getStatus());
        
        orderService.processOrder(order);
        assertEquals("PROCESSING", order.getStatus());
        
        orderService.deliverOrder(order);
        assertEquals("DELIVERED", order.getStatus());
    }
}

@SpringBootTest
public class TaxStrategyTest {
    
    @Autowired
    @Qualifier("vatTaxStrategy")
    private TaxStrategy vatStrategy;
    
    @Test
    public void testVATCalculation() {
        double tax = vatStrategy.calculateTax(1000);
        assertEquals(100, tax, 0.01);
    }
}
```

---

## 📖 Tài liệu tham khảo

### Design Patterns
- 📘 Gang of Four - "Design Patterns: Elements of Reusable Object-Oriented Software"
- 🔗 https://refactoring.guru/design-patterns

### Spring Framework
- 🔗 https://spring.io/projects/spring-boot
- 🔗 https://docs.spring.io/spring-framework/reference/core.html

### Java
- 🔗 https://docs.oracle.com/javase/tutorial/
- 🔗 https://www.oracle.com/java/technologies/

---

## 📝 Ghi chú Quan trọng

### ✅ Điểm mạnh của cách triển khai này
1. **Sử dụng Spring DI** thay vì tạo object thủ công
2. **CommandLineRunner** thay thế main() console
3. **REST API** để minh họa
4. **Decoupled architecture** - dễ bảo trì, mở rộng
5. **Tài liệu chi tiết** DESIGN_PATTERNS_GUIDE.md

### ⚠️ Lưu ý
- Các state class sử dụng `new` (không inject) vì chúng chỉ là data holder
- TaxStrategy được inject qua @Qualifier để chọn implementation cụ thể
- Decorator được tạo động tùy theo nhu cầu

---

## 🔄 Quy trình Phát triển

### Bước 1: Xác định Pattern phù hợp
```
Problem → Phân tích → Chọn Pattern
```

### Bước 2: Thiết kế Interface
```
Interface → Concrete Implementation → Testing
```

### Bước 3: Integrate với Spring
```
@Component/@Service → @Autowired → Inject dependency
```

### Bước 4: Create REST API
```
@RestController → @GetMapping → Endpoint
```

---

## 🏆 Kết luận

Bài tập này minh họa cách kết hợp 3 Design Pattern cơ bản với Spring Boot DI/IoC:

| Pattern | Vấn đề | Giải pháp | Lợi ích |
|---------|--------|----------|---------|
| **State** | Behavior thay đổi theo state | Encapsulate state logic | Dễ mở rộng state |
| **Strategy** | Thuật toán linh hoạt | Swap algorithm runtime | Không phụ thuộc client |
| **Decorator** | Thêm tính năng động | Wrap object với tính năng mới | Tránh class explosion |

**Spring DI/IoC** giúp:
- ✓ Quản lý dependency tự động
- ✓ Tăng flexibility & extensibility
- ✓ Dễ test & maintain
- ✓ Loose coupling

---

## 👨‍💻 Tác giả
**Võ Phước Việt**  
HCMUT - Tuần 2,3 Design Pattern

---

## 📄 License
MIT License - Tự do sử dụng cho học tập và phát triển

