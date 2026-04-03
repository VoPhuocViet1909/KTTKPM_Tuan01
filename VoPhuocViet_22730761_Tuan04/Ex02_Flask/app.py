from flask import Flask
import os

app = Flask(__name__)

@app.route('/')
def hello():
    return "Hello, Docker Flask!"

if __name__ == "__main__":
    # Flask mặc định chạy cổng 5000
    # Host 0.0.0.0 để có thể truy cập từ ngoài container
    app.run(host='0.0.0.0', port=5000)