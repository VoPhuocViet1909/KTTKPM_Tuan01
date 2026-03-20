# 🎯 GIẢI PHÁP: 3 DESIGN PATTERN TRONG SPRING BOOT

## ✨ Tổng Quan Dự Án

Dự án này giải quyết **3 bài toán thực tế** bằng **3 Design Pattern** khác nhau, được triển khai hoàn toàn trong **Spring Boot** với **Dependency Injection (DI)** và **Inversion of Control (IoC)**, thay thế cấu trúc `main()` console thông thường.

---

## 📋 Danh Sách File Được Tạo

### 📁 Tài Liệu
```
✅ README.md                      - Hướng dẫn chi tiết cách chạy & triển khai
✅ DESIGN_PATTERNS_GUIDE.md       - Tài liệu đầy đủ 3 Pattern (30+ trang)
✅ SUMMARY.md                     - Tóm tắt ngắn gọn các điểm chính
✅ QUICK_START.md                 - Quick start guide
```

### 📂 BÀI TOÁN 1: STATE PATTERN
**File đã tạo:**
```
✅ OrderState.java                - Interface định nghĩa behavior
✅ Order.java                     - Context class (quản lý state)
✅ OrderService.java              - @Service class
✅ NewOrderState.java             - @Component (Concrete State)
✅ ProcessingOrderState.java      - @Component (Concrete State)
✅ DeliveredOrderState.java       - @Component (Concrete State)
✅ CancelledOrderState.java       - @Component (Concrete State)
```

**Mục đích:** Quản lý trạng thái Order (NEW → PROCESSING → DELIVERED/CANCELLED)

---

### 📂 BÀI TOÁN 2: STRATEGY PATTERN
**File đã tạo:**
```
✅ TaxStrategy.java               - Interface định nghĩa thuật toán
✅ Product.java                   - Context class (sử dụng strategy)
✅ VATTaxStrategy.java            - @Component (VAT 10%)
✅ ConsumptionTaxStrategy.java    - @Component (Consumption 5%)
✅ LuxuryTaxStrategy.java         - @Component (Luxury 15%)
```

**Mục đích:** Tính thuế linh hoạt tùy loại sản phẩm

---

### 📂 BÀI TOÁN 3: DECORATOR PATTERN
**File đã tạo:**
```
✅ PaymentMethod.java             - Interface cơ bản
✅ PaymentDecorator.java          - Abstract Decorator
✅ CreditCardPayment.java         - @Component (Concrete Component)
✅ ProcessingFeeDecorator.java    - Decorator (thêm phí 2%)
✅ DiscountCodeDecorator.java     - Decorator (thêm giảm giá)
```

**Mục đích:** Bọc thêm tính năng vào thanh toán gốc

---

### 🔧 Hệ Thống
**File đã tạo:**
```
✅ DesignPatternDemo.java         - @Component CommandLineRunner (thay main())
✅ PatternDemoController.java     - @RestController với 4 endpoint API
✅ DesignPatternTests.java        - Unit Tests cho tất cả pattern
```

---

## 🎯 KẾT QUẢ CHÍNH

### 1️⃣ STATE PATTERN - Quản Lý Đơn Hàng

**Kết quả:**
- Order có 4 trạng thái: NEW, PROCESSING, DELIVERED, CANCELLED
- Mỗi state có hành vi riêng (process, deliver, cancel)
- Tránh được if-else phức tạp

**Ví dụ sử dụng:**
```java
Order order = new Order("ORD-001");
orderService.processOrder(order);    // NEW → PROCESSING
orderService.deliverOrder(order);    // PROCESSING → DELIVERED
orderService.cancelOrder(order);     // Lỗi: không thể cancel DELIVERED
```

---

### 2️⃣ STRATEGY PATTERN - Tính Thuế

**Kết quả:**
- Hỗ trợ 3 loại thuế: VAT (10%), Consumption (5%), Luxury (15%)
- Dễ thêm loại thuế mới
- Có thể thay đổi strategy runtime

**Ví dụ sử dụng:**
```java
Product phone = new Product("P001", "Điện thoại", 1000);
phone.setTaxStrategy(vatStrategy);           // 10% VAT
System.out.println(phone.calculateTotalPrice());  // 1100

phone.setTaxStrategy(luxuryStrategy);        // 15% Luxury
System.out.println(phone.calculateTotalPrice());  // 1150
```

---

### 3️⃣ DECORATOR PATTERN - Hệ Thống Thanh Toán

**Kết quả:**
- Thanh toán cơ bản: Credit Card = 1000
- Thêm Processing Fee (2%): 1000 + 20 = 1020
- Thêm Discount (10%): 1020 - 102 = 918

**Ví dụ sử dụng:**
```java
// Cơ bản
PaymentMethod payment1 = new CreditCardPayment();
payment1.calculateAmount(1000);  // 1000

// Thêm phí
PaymentMethod payment2 = new ProcessingFeeDecorator(new CreditCardPayment());
payment2.calculateAmount(1000);  // 1020

// Thêm phí + giảm giá
PaymentMethod payment3 = new DiscountCodeDecorator(
    new ProcessingFeeDecorator(new CreditCardPayment()),
    0.10
);
payment3.calculateAmount(1000);  // 918
```

---

## 🔌 SPRING BOOT DI/IoC

### Cách Sử Dụng

**1. Inject OrderService:**
```java
@Autowired
private OrderService orderService;
```

**2. Inject Strategy với @Qualifier:**
```java
@Autowired
@Qualifier("vatTaxStrategy")
private TaxStrategy vatStrategy;

@Autowired
@Qualifier("luxuryTaxStrategy")
private TaxStrategy luxuryStrategy;
```

**3. CommandLineRunner thay main():**
```java
@Component
public class DesignPatternDemo implements CommandLineRunner {
    @Override
    public void run(String... args) {
        // Spring tự động gọi khi khởi động
        demonstrateStatePattern();
        demonstrateStrategyPattern();
        demonstrateDecoratorPattern();
    }
}
```

---

## 🚀 Cách Chạy

### 1. Compile
```bash
cd VoPhuocViet_Tuan2_3Pattern
mvnw clean compile
```

### 2. Chạy Application
```bash
mvnw spring-boot:run
```

**Output console:**
```
================================================================================
DEMO: 3 DESIGN PATTERN TRONG SPRING BOOT
================================================================================

┌─── BÀI TOÁN 1: STATE PATTERN ───┐
│ Hệ thống quản lý đơn hàng        │
└──────────────────────────────────┘

PHÂN TÍCH:
• Bài toán: Hành vi Order thay đổi tùy trạng thái...
...
```

### 3. REST API
```
http://localhost:8080/api/demo/all-patterns
http://localhost:8080/api/demo/state-pattern/order
http://localhost:8080/api/demo/strategy-pattern/tax
http://localhost:8080/api/demo/decorator-pattern/payment
```

### 4. Chạy Tests
```bash
mvnw test
```

---

## 📊 Lợi Ích Kết Hợp Pattern + Spring

| Lợi Ích | Chi Tiết |
|---------|----------|
| **DI Management** | @Service, @Component, @Autowired quản lý lifecycle |
| **IoC Container** | Spring tạo & quản lý object, không cần `new` |
| **Flexibility** | Thêm state/strategy mới chỉ cần @Component |
| **Extensibility** | Thay đổi implementation không sửa code cũ |
| **Loose Coupling** | Phụ thuộc interface, không concrete class |
| **Testability** | Dễ mock khi test, inject mock object |
| **Configuration** | Tập trung quản lý, thay đổi không cần recompile |

---

## 📁 Cấu Trúc Project Hoàn Chỉnh

```
VoPhuocViet_Tuan2_3Pattern/
│
├── 📄 pom.xml                         (Maven Config)
├── 📄 README.md                       (Hướng dẫn chính)
├── 📄 DESIGN_PATTERNS_GUIDE.md        (Tài liệu chi tiết)
├── 📄 SUMMARY.md                      (Tóm tắt)
├── 📄 QUICK_START.md                  (Quick start)
│
├── mvnw / mvnw.cmd                    (Maven Wrapper)
│
└── src/
    ├── main/java/com/fit/vophuocviet_tuan2_3pattern/
    │   │
    │   ├── 🔵 DesignPatternDemo.java              (CommandLineRunner)
    │   ├── VoPhuocVietTuan23PatternApplication.java
    │   │
    │   ├── controllers/
    │   │   └── PatternDemoController.java         (REST API)
    │   │
    │   ├── problem1_state/          (STATE PATTERN)
    │   │   ├── OrderState.java
    │   │   ├── Order.java
    │   │   ├── OrderService.java    (@Service)
    │   │   ├── NewOrderState.java   (@Component)
    │   │   ├── ProcessingOrderState.java
    │   │   ├── DeliveredOrderState.java
    │   │   └── CancelledOrderState.java
    │   │
    │   ├── problem2_strategy/       (STRATEGY PATTERN)
    │   │   ├── TaxStrategy.java
    │   │   ├── Product.java
    │   │   ├── VATTaxStrategy.java  (@Component)
    │   │   ├── ConsumptionTaxStrategy.java
    │   │   └── LuxuryTaxStrategy.java
    │   │
    │   └── problem3_decorator/      (DECORATOR PATTERN)
    │       ├── PaymentMethod.java
    │       ├── PaymentDecorator.java
    │       ├── CreditCardPayment.java (@Component)
    │       ├── ProcessingFeeDecorator.java
    │       └── DiscountCodeDecorator.java
    │
    ├── test/java/
    │   └── com/fit/.../DesignPatternTests.java  (Unit Tests)
    │
    └── resources/
        └── application.properties
```

---

## 🎓 Kết Luận

### 3 Pattern Giải Quyết 3 Vấn Đề:
1. **STATE**: Behavior thay đổi theo state
2. **STRATEGY**: Thuật toán linh hoạt có thể swap
3. **DECORATOR**: Thêm tính năng động

### Spring DI/IoC Cung Cấp:
1. **Quản lý dependency tự động**
2. **Code dễ maintain & test**
3. **Flexible & extensible**
4. **Loose coupling**

### Kết Hợp Đôi:
```
Design Pattern (Cách suy nghĩ)
        +
Spring DI/IoC (Công cụ thực thi)
        =
Scalable, Maintainable, Testable Code
```

---

## 📚 File Tài Liệu Chi Tiết

| File | Nội Dung |
|------|---------|
| **README.md** | Hướng dẫn chạy, cấu trúc project, lợi ích |
| **DESIGN_PATTERNS_GUIDE.md** | Phân tích chi tiết 3 pattern (30+ trang) |
| **SUMMARY.md** | Tóm tắt ngắn gọn tất cả điểm chính |
| **QUICK_START.md** | Bắt đầu nhanh trong 5 phút |

---

## ✅ Checklist Hoàn Thành

- ✅ Bài toán 1: STATE PATTERN (7 file)
- ✅ Bài toán 2: STRATEGY PATTERN (5 file)
- ✅ Bài toán 3: DECORATOR PATTERN (5 file)
- ✅ Demo via CommandLineRunner
- ✅ Demo via REST API (4 endpoint)
- ✅ Unit Tests (14 test cases)
- ✅ Tài liệu chi tiết (4 file markdown)
- ✅ Spring DI/IoC integration
- ✅ @Component, @Service, @Autowired, @Qualifier

---

## 👨‍💻 Thông Tin

**Tác giả:** Võ Phước Việt  
**Khóa:** HCMUT - Design Pattern (Tuần 2, 3)  
**Framework:** Spring Boot 4.0.4  
**Java:** Java 17+  

**Liên hệ:** Xem README.md & DESIGN_PATTERNS_GUIDE.md

---

## 🎯 Mục Tiêu Đạt Được

✅ **Phân tích:** Chọn Pattern phù hợp cho 3 bài toán  
✅ **Code Spring Boot:** Implement các interface & concrete class  
✅ **Context/Service:** Viết class Context & Service  
✅ **REST API:** Minh họa cách Client gọi API  
✅ **Kết luận:** Giải thích lợi ích kết hợp Pattern + DI  

---

**Chuẩn bị hoàn tất! 🚀**

