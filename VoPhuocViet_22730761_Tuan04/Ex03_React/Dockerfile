# 1. Sử dụng node:18-alpine làm base image (siêu nhẹ)
FROM node:18-alpine

# 2. Thiết lập thư mục làm việc
WORKDIR /app

# 3. Copy file quản lý thư viện vào trước
COPY package.json .

# 4. Cài đặt dependencies
RUN npm install

# 5. Copy toàn bộ code vào container
COPY . .

# 6. React mặc định chạy cổng 3000
EXPOSE 3000

# 7. Lệnh khởi chạy
CMD ["npm", "start"]