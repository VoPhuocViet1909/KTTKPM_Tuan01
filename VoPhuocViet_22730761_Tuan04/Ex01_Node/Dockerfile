# 1. Sử dụng node:18 làm base image theo yêu cầu
FROM node:18

# 2. Thiết lập thư mục làm việc bên trong container
WORKDIR /app

# 3. Copy package.json vào trước để tận dụng Docker layer caching cho dependencies
COPY package.json .

# 4. Cài đặt các dependencies (ở bài này thực tế chưa có lib ngoài nhưng vẫn là best practice)
RUN npm install

# 5. Copy toàn bộ mã nguồn còn lại vào container
COPY . .

# 6. Mở cổng 3000 để truy cập từ bên ngoài
EXPOSE 3000

# 7. Lệnh khởi chạy ứng dụng
CMD ["npm", "start"]