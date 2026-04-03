# 1. Sử dụng redis:latest làm base image
FROM redis:latest

# 2. Tạo thư mục để chứa file cấu hình (thường là /usr/local/etc/redis)
WORKDIR /usr/local/etc/redis

# 3. Copy file cấu hình từ máy host vào container
COPY redis.conf .

# 4. Mở cổng mặc định của Redis
EXPOSE 6379

# 5. Lệnh khởi chạy Redis và chỉ định file cấu hình vừa copy vào
# Cú pháp: redis-server [đường-dẫn-file-conf]
CMD ["redis-server", "/usr/local/etc/redis/redis.conf"]