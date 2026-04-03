# 1. Sử dụng python:3.9 làm base image
FROM python:3.9-slim

# 2. Thiết lập thư mục làm việc
WORKDIR /app

# 3. Thiết lập biến môi trường mặc định theo yêu cầu đề bài
ENV APP_ENV=development

# 4. Copy mã nguồn vào
COPY app.py .

# 5. Chạy ứng dụng
CMD ["python", "app.py"]