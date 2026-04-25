const jwt = require('jsonwebtoken');

const authMiddleware = (req, res, next) => {
    try {
        // Lấy token từ header Authorization: Bearer <token>
        const authHeader = req.headers.authorization;
        if (!authHeader || !authHeader.startsWith('Bearer ')) {
            return res.status(401).json({ message: 'Không có token, quyền truy cập bị từ chối' });
        }

        const token = authHeader.split(' ')[1];
        
        // Verify token (bắt buộc phải có JWT_SECRET trong biến môi trường)
        const decoded = jwt.verify(token, process.env.JWT_SECRET);
        
        // Gắn thông tin user đã giải mã vào request object
        req.user = decoded;
        next();
    } catch (error) {
        return res.status(401).json({ message: 'Token không hợp lệ hoặc đã hết hạn' });
    }
};

module.exports = authMiddleware;
