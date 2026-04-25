const express = require('express');
const router = express.Router();
const userController = require('../controllers/userController');
const authMiddleware = require('../middlewares/auth');

// Đăng ký tài khoản (Public)
router.post('/register', userController.register);

// Đăng nhập trả về Token (Public)
router.post('/login', userController.login);

// Lấy thông tin cá nhân hiện tại (Protected)
// LƯU Ý: Phải đặt route `/me` trên `/:id` để tránh express match `me` là params `id`
router.get('/me', authMiddleware, userController.getMe);

// Lấy thông tin cụ thể của người dùng theo ID (Public/Internal)
router.get('/:id', userController.getUserById);

// Lấy danh sách toàn bộ người dùng (Public/Internal)
router.get('/', userController.getAllUsers);

module.exports = router;
