require('dotenv').config();
const express = require('express');
const cors = require('cors');
const sequelize = require('./config/database');
const userRoutes = require('./routes/userRoutes');

const app = express();

// Middlewares cấu hình
app.use(cors());
app.use(express.json()); // Phân tích JSON body
app.use(express.urlencoded({ extended: true }));

// Khai báo các Routes
app.use('/api/users', userRoutes);

// Middleware xử lý lỗi khi gọi một API route không tồn tại
app.use((req, res, next) => {
    res.status(404).json({ message: 'API Route không tồn tại' });
});

// Port chạy Server
const PORT = process.env.PORT || 8081;

// Hàm khởi chạy db và server
const startServer = async () => {
    try {
        // Kiểm tra kết nối tới MySQL
        await sequelize.authenticate();
        console.log('Kết nối Database thành công.');
        
        // Đồng bộ các models thành tables
        await sequelize.sync({ alter: true }); // Dùng { force: true } nếu muốn drop tables và tạo lại
        console.log('Đã cập nhật đồng bộ các biểu mẫu Model với DB.');

        // Khởi động Express
        app.listen(PORT, () => {
            console.log(`🚀 User Service Microservice đang chạy tại Port: ${PORT}`);
        });
    } catch (error) {
        console.error('Không thể kết nối đến Database hoặc khởi động server:', error);
        process.exit(1); 
    }
};

startServer();
