# 1. Sử dụng php:8.2-apache làm base image theo yêu cầu
FROM php:8.2-apache

# 2. Apache trong image này mặc định đọc file tại /var/www/html/
# Thiết lập thư mục làm việc
WORKDIR /var/www/html/

# 3. Copy mã nguồn vào (để image vẫn có thể chạy độc lập)
COPY . .

# 4. Mở cổng 80 (cổng mặc định của web server)
EXPOSE 80