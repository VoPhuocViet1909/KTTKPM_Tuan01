import os

# Đọc biến môi trường tên là APP_ENV, nếu không có thì mặc định là 'not_set'
app_env = os.getenv('APP_ENV', 'not_set')

print(f"Ứng dụng đang chạy trong môi trường: {app_env}")