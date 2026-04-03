# 1. Sử dụng postgres:15 làm base image theo yêu cầu
FROM postgres:15

# 2. Thiết lập các biến môi trường bắt buộc cho Postgres
# (Bạn có thể đổi mật khẩu tùy ý)
ENV POSTGRES_DB=mydb
ENV POSTGRES_USER=myuser
ENV POSTGRES_PASSWORD=mypassword

# 3. Copy file SQL vào thư mục đặc biệt của Postgres:
# /docker-entrypoint-initdb.d/
# Bất kỳ file .sql nào nằm trong đây sẽ được Postgres tự động thực thi 
# CHỈ trong lần đầu tiên container khởi tạo database.
COPY init.sql /docker-entrypoint-initdb.d/

# 4. Postgres mặc định chạy cổng 5432
EXPOSE 5432