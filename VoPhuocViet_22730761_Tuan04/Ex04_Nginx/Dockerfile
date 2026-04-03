# 1. Sử dụng nginx:latest làm base image theo yêu cầu
FROM nginx:latest

# 2. Copy file index.html từ máy host vào đúng thư mục mặc định của Nginx trong container
# Đường dẫn mặc định để Nginx đọc file web là /usr/share/nginx/html
COPY index.html /usr/share/nginx/html/index.html

# 3. Nginx mặc định chạy trên cổng 80 bên trong container
EXPOSE 80