# 📑 INDEX: Hướng Dẫn Tìm Kiếm Tài Liệu

## 🚀 Bắt Đầu Nhanh
- **Đọc đầu tiên:** [QUICK_START.md](./QUICK_START.md) - 5 phút để hiểu tổng quan
- **Cách chạy:** [README.md](./README.md) - Hướng dẫn chi tiết từng bước

---

## 📚 Tài Liệu Chi Tiết

### Tài Liệu Tổng Quát
- [README.md](./README.md) - Hướng dẫn đầy đủ
- [QUICK_START.md](./QUICK_START.md) - Quick start guide
- [SUMMARY.md](./SUMMARY.md) - Tóm tắt ngắn gọn
- [DESIGN_PATTERNS_GUIDE.md](./DESIGN_PATTERNS_GUIDE.md) - Hướng dẫn chi tiết 30+ trang

---

## 🎯 Thư Mục Bài Toán

### BÀI TOÁN 1: STATE PATTERN
**Thư mục:** `src/main/java/.../problem1_state/`

| File | Mục Đích |
|------|---------|
| `OrderState.java` | Interface định nghĩa các action |
| `Order.java` | Context class (quản lý state) |
| `OrderService.java` | Service xử lý business logic (@Service) |
| `NewOrderState.java` | State ban đầu (@Component) |
| `ProcessingOrderState.java` | State đang xử lý (@Component) |
| `DeliveredOrderState.java` | State đã giao (@Component) |
| `CancelledOrderState.java` | State đã hủy (@Component) |

**Khái niệm:** Hành vi Object thay đổi tùy State

---

### BÀI TOÁN 2: STRATEGY PATTERN
**Thư mục:** `src/main/java/.../problem2_strategy/`

| File | Mục Đích |
|------|---------|
| `TaxStrategy.java` | Interface định nghĩa thuật toán |
| `Product.java` | Context class sử dụng strategy |
| `VATTaxStrategy.java` | Thuế VAT 10% (@Component) |
| `ConsumptionTaxStrategy.java` | Thuế tiêu thụ 5% (@Component) |
| `LuxuryTaxStrategy.java` | Thuế xa xỉ 15% (@Component) |

**Khái niệm:** Encapsulate các thuật toán có thể swap

---

### BÀI TOÁN 3: DECORATOR PATTERN
**Thư mục:** `src/main/java/.../problem3_decorator/`

| File | Mục Đích |
|------|---------|
| `PaymentMethod.java` | Interface cơ bản |
| `PaymentDecorator.java` | Abstract decorator class |
| `CreditCardPayment.java` | Concrete component (@Component) |
| `ProcessingFeeDecorator.java` | Thêm phí xử lý 2% |
| `DiscountCodeDecorator.java` | Thêm mã giảm giá |

**Khái niệm:** Thêm tính năng động mà không sửa class gốc

---

## 🔌 Spring Integration

### Cách Chạy Ứng Dụng

**File chính:** `DesignPatternDemo.java`
```
- Là @Component CommandLineRunner
- Thay thế main() console thông thường
- Spring tự động gọi khi khởi động
```

**REST API:** `PatternDemoController.java`
```
- @RestController với 4 endpoint
- Minh họa cách Client gọi Pattern
- Tại http://localhost:8080/api/demo
```

---

## 📋 Lựa Chọn Nhanh

### Tôi muốn...

#### 1. **Bắt đầu nhanh**
→ Đọc [QUICK_START.md](./QUICK_START.md)

#### 2. **Hiểu chi tiết 3 Pattern**
→ Đọc [DESIGN_PATTERNS_GUIDE.md](./DESIGN_PATTERNS_GUIDE.md)

#### 3. **Cách chạy ứng dụng**
→ Đọc [README.md](./README.md) → Mục "Cách chạy ứng dụng"

#### 4. **Tìm code State Pattern**
→ Thư mục `problem1_state/` → Xem `OrderState.java`

#### 5. **Tìm code Strategy Pattern**
→ Thư mục `problem2_strategy/` → Xem `TaxStrategy.java`

#### 6. **Tìm code Decorator Pattern**
→ Thư mục `problem3_decorator/` → Xem `PaymentDecorator.java`

#### 7. **Chạy Unit Tests**
→ `mvnw test` → Xem `DesignPatternTests.java`

#### 8. **Truy cập REST API**
→ Chạy `mvnw spring-boot:run` → Truy cập `http://localhost:8080/api/demo/all-patterns`

---

## 🏗️ Cấu Trúc Thư Mục

```
VoPhuocViet_Tuan2_3Pattern/
│
├── 📄 Tài liệu chính
│   ├── README.md                      ← Đọc đầu tiên!
│   ├── QUICK_START.md                 ← Quick guide
│   ├── SUMMARY.md                     ← Tóm tắt
│   ├── DESIGN_PATTERNS_GUIDE.md       ← Chi tiết
│   └── INDEX.md                       ← File này
│
├── 🔵 BÀI TOÁN 1: STATE PATTERN
│   └── src/main/java/.../problem1_state/
│       ├── OrderState.java
│       ├── Order.java
│       ├── OrderService.java
│       └── {New,Processing,Delivered,Cancelled}OrderState.java
│
├── 🟢 BÀI TOÁN 2: STRATEGY PATTERN
│   └── src/main/java/.../problem2_strategy/
│       ├── TaxStrategy.java
│       ├── Product.java
│       └── {VAT,Consumption,Luxury}TaxStrategy.java
│
├── 🔴 BÀI TOÁN 3: DECORATOR PATTERN
│   └── src/main/java/.../problem3_decorator/
│       ├── PaymentMethod.java
│       ├── PaymentDecorator.java
│       ├── CreditCardPayment.java
│       └── {ProcessingFee,DiscountCode}Decorator.java
│
└── 🔧 HỆ THỐNG
    ├── DesignPatternDemo.java         ← CommandLineRunner
    ├── PatternDemoController.java     ← REST API
    └── DesignPatternTests.java        ← Unit Tests
```

---

## 🎯 Mục Tiêu Học Tập

| Mục Tiêu | Tài Liệu | File Code |
|----------|----------|----------|
| **Hiểu State Pattern** | DESIGN_PATTERNS_GUIDE.md → Phần 1 | problem1_state/ |
| **Hiểu Strategy Pattern** | DESIGN_PATTERNS_GUIDE.md → Phần 2 | problem2_strategy/ |
| **Hiểu Decorator Pattern** | DESIGN_PATTERNS_GUIDE.md → Phần 3 | problem3_decorator/ |
| **Cách sử dụng Spring DI** | README.md → Mục "Spring DI/IoC" | DesignPatternDemo.java |
| **Cách viết REST API** | README.md → Mục "REST API" | PatternDemoController.java |
| **Cách test Pattern** | README.md → Mục "Testing" | DesignPatternTests.java |

---

## 💡 Gợi Ý Học Tập

### Lần 1: Tổng Quát (10 phút)
1. Đọc [QUICK_START.md](./QUICK_START.md)
2. Chạy `mvnw spring-boot:run`
3. Xem console output

### Lần 2: Chi Tiết Pattern (30 phút)
1. Đọc [DESIGN_PATTERNS_GUIDE.md](./DESIGN_PATTERNS_GUIDE.md)
2. Xem code từng Pattern
3. Chạy REST API test

### Lần 3: Deep Dive (1 giờ)
1. Đọc [README.md](./DESIGN_PATTERNS_GUIDE.md) hoàn chỉnh
2. Viết test case riêng
3. Thêm Pattern/State/Strategy mới

---

## 🔗 Quick Links

### Documentation
- 📘 [README.md](./README.md) - Main guide
- 📕 [DESIGN_PATTERNS_GUIDE.md](./DESIGN_PATTERNS_GUIDE.md) - Detailed guide
- 📗 [QUICK_START.md](./QUICK_START.md) - Quick start
- 📙 [SUMMARY.md](./SUMMARY.md) - Summary

### Source Code
- 🔵 [problem1_state/](./src/main/java/com/fit/vophuocviet_tuan2_3pattern/problem1_state/) - State Pattern
- 🟢 [problem2_strategy/](./src/main/java/com/fit/vophuocviet_tuan2_3pattern/problem2_strategy/) - Strategy Pattern
- 🔴 [problem3_decorator/](./src/main/java/com/fit/vophuocviet_tuan2_3pattern/problem3_decorator/) - Decorator Pattern

### Key Files
- 🎯 [DesignPatternDemo.java](./src/main/java/com/fit/vophuocviet_tuan2_3pattern/DesignPatternDemo.java) - Main runner
- 🌐 [PatternDemoController.java](./src/main/java/com/fit/vophuocviet_tuan2_3pattern/controllers/PatternDemoController.java) - REST API
- 🧪 [DesignPatternTests.java](./src/test/java/com/fit/vophuocviet_tuan2_3pattern/DesignPatternTests.java) - Unit tests

---

## ❓ FAQ

**Q: Bắt đầu từ đâu?**
A: Đọc [QUICK_START.md](./QUICK_START.md) trong 5 phút

**Q: Muốn hiểu chi tiết Pattern?**
A: Xem [DESIGN_PATTERNS_GUIDE.md](./DESIGN_PATTERNS_GUIDE.md)

**Q: Cách chạy ứng dụng?**
A: `mvnw spring-boot:run` (xem [README.md](./README.md))

**Q: REST API ở đâu?**
A: `http://localhost:8080/api/demo/all-patterns`

**Q: Cách test?**
A: `mvnw test`

---

## 📞 Thông Tin

**Tác giả:** Võ Phước Việt  
**Khóa:** HCMUT - Design Pattern (Tuần 2, 3)  
**Framework:** Spring Boot 4.0.4  
**Java Version:** 17+

---

**Hãy bắt đầu với [QUICK_START.md](./QUICK_START.md)! 🚀**

