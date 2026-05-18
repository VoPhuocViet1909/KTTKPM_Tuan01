# Hướng Dẫn Import Postman Collection

## Cách Import

### Bước 1: Import Environment
1. Mở Postman
2. Click **Environments** (góc trên bên phải, icon bánh răng)
3. Click **Import**
4. Chọn file: `Microservices_Environment.postman_environment.json`
5. Click **Save**

### Bước 2: Import Collection
1. Trong Postman, click **Collections** (tab bên trái)
2. Click **Import**
3. Chọn file: `Microservices_API.postman_collection.json`
4. Collection sẽ xuất hiện trong danh sách Collections

### Bước 3: Chọn Environment
Ở góc trên bên phải Postman, chọn environment **Microservices Environment** từ dropdown.

---

## Cách Sử Dụng

### 1. Đăng nhập và lấy Token
1. Mở folder **USER-SERVICE** → **Authentication**
2. Click request **POST /api/v1/auth/login**
3. Điều chỉnh email/password nếu cần
4. Click **Send**
5. Sau khi nhận được response thành công:
   - Copy giá trị `data.accessToken` từ response
   - Click biến **accessToken** trong Environment (icon bánh răng → Environments → Microservices Environment)
   - Paste token vào ô **Current Value** của `accessToken`
   - Tương tự copy `data.refreshToken` vào `refreshToken`

### 2. Test các API có xác thực
Sau khi đã có `accessToken`, các request có icon ổ khóa 🔒 sẽ tự động sử dụng Bearer token.

### 3. Test các API công khai
Các request không có ổ khóa (như GET categories, GET products) không cần token.

---

## Cấu trúc Collection

```
Microservices_API/
├── USER-SERVICE/
│   ├── Authentication/
│   │   ├── POST /api/v1/auth/login
│   │   ├── POST /api/v1/auth/refresh-token
│   │   └── POST /api/v1/auth/logout
│   └── User Management/
│       ├── POST /api/v1/users
│       ├── GET /api/v1/users
│       └── GET /api/v1/users/me
└── PRODUCT-SERVICE/
    ├── Category/
    │   ├── POST /api/v1/categories
    │   ├── GET /api/v1/categories
    │   ├── PUT /api/v1/categories/:id
    │   └── DELETE /api/v1/categories/:id
    └── Product/
        ├── POST /api/v1/products
        ├── GET /api/v1/products
        ├── GET /api/v1/products/:id
        └── DELETE /api/v1/products/:id
```

---

## Danh Sách Tất Cả API Endpoints

### Authentication (3 endpoints)
| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| POST | `/api/v1/auth/login` | Không | Đăng nhập |
| POST | `/api/v1/auth/refresh-token` | Không | Làm mới token |
| POST | `/api/v1/auth/logout` | Không | Đăng xuất |

### User Management (3 endpoints)
| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| POST | `/api/v1/users` | Bearer | Tạo user mới |
| GET | `/api/v1/users` | Bearer | Lấy danh sách user |
| GET | `/api/v1/users/me` | Bearer | Lấy thông tin user hiện tại |

### Category (4 endpoints)
| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| POST | `/api/v1/categories` | Bearer | Tạo danh mục |
| GET | `/api/v1/categories` | Không | Lấy danh sách danh mục |
| PUT | `/api/v1/categories/:id` | Bearer | Cập nhật danh mục |
| DELETE | `/api/v1/categories/:id` | Bearer | Xóa danh mục |

### Product (4 endpoints)
| Method | Endpoint | Auth | Mô tả |
|--------|----------|------|-------|
| POST | `/api/v1/products` | Bearer | Tạo sản phẩm |
| GET | `/api/v1/products` | Không | Tìm/lọc sản phẩm |
| GET | `/api/v1/products/:id` | Không | Lấy chi tiết sản phẩm |
| DELETE | `/api/v1/products/:id` | Bearer | Xóa sản phẩm |

---

## Các Biến Môi Trường

| Biến | Mô tả |
|------|-------|
| `baseUrl` | URL gốc của API Gateway (mặc định: http://localhost:9191) |
| `accessToken` | JWT access token (sau khi đăng nhập) |
| `refreshToken` | Refresh token (sau khi đăng nhập) |
| `userId` | User ID (sau khi tạo user) |
| `categoryId` | Category ID (sau khi tạo category) |
| `productId` | Product ID (sau khi tạo product) |
| `userEmail` | Email đăng nhập mặc định |
| `userPassword` | Password đăng nhập mặc định |

---

## Lưu Ý Quan Trọng

1. **API Gateway Port**: Tất cả request đều phải qua API Gateway ở port `9191`
2. **JWT Token**: Token có thời hạn, nếu hết hạn hãy dùng `refresh-token` hoặc đăng nhập lại
3. **ProductStatus enum**: `ACTIVE`, `INACTIVE`, `DISCONTINUED`
4. **UserStatus enum**: Kiểm tra trong source code của user-service
5. **Tạo Product**: Cần có `categoryId` hợp lệ trước khi tạo sản phẩm
