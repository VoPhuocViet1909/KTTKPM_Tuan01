const User = require('../models/user');
const bcrypt = require('bcryptjs');
const jwt = require('jsonwebtoken');

/**
 * Đăng ký tài khoản người dùng mới
 * POST /api/users/register
 */
const register = async (req, res) => {
    try {
        const { username, email, password } = req.body;

        // Kiểm tra email đã tồn tại chưa
        const existingUser = await User.findOne({ where: { email } });
        if (existingUser) {
            return res.status(400).json({ message: 'Email đã tồn tại' });
        }

        // Hash mật khẩu với bcrypt
        const salt = await bcrypt.genSalt(10);
        const hashedPassword = await bcrypt.hash(password, salt);

        // Tạo user mới trong cơ sở dữ liệu
        const newUser = await User.create({
            username,
            email,
            password: hashedPassword
        });

        // Loại bỏ trường password trước khi trả về response
        const userResponse = newUser.toJSON();
        delete userResponse.password;

        res.status(201).json({
            message: 'Đăng ký thành công',
            user: userResponse
        });
    } catch (error) {
        console.error('Lỗi API Đăng ký:', error);
        res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
    }
};

/**
 * Đăng nhập trả về JWT Token
 * POST /api/users/login
 */
const login = async (req, res) => {
    try {
        const { email, password } = req.body;

        // Tìm user theo email
        const user = await User.findOne({ where: { email } });
        if (!user) {
            return res.status(400).json({ message: 'Email hoặc mật khẩu không đúng' });
        }

        // Kiểm tra khớp mật khẩu
        const isMatch = await bcrypt.compare(password, user.password);
        if (!isMatch) {
            return res.status(400).json({ message: 'Email hoặc mật khẩu không đúng' });
        }

        // Tạo JWT token với thời hạn 7 ngày
        const payload = {
            id: user.id,
            username: user.username,
            email: user.email
        };

        const token = jwt.sign(payload, process.env.JWT_SECRET, { expiresIn: '7d' });

        // Loại bỏ password trước khi trả về user
        const userResponse = user.toJSON();
        delete userResponse.password;

        res.status(200).json({
            message: 'Đăng nhập thành công',
            token,
            user: userResponse
        });
    } catch (error) {
        console.error('Lỗi API Đăng nhập:', error);
        res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
    }
};

/**
 * Lấy thông tin user hiện tại đang đăng nhập theo token
 * GET /api/users/me
 */
const getMe = async (req, res) => {
    try {
        // req.user được Inject từ authMiddleware
        const user = await User.findByPk(req.user.id, {
            attributes: { exclude: ['password'] } // Không trả về password
        });

        if (!user) {
            return res.status(404).json({ message: 'Không tìm thấy người dùng' });
        }

        res.status(200).json(user);
    } catch (error) {
        console.error('Lỗi API Lấy thông tin bản thân:', error);
        res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
    }
};

/**
 * Lấy thông tin cụ thể của user bằng ID (hữu ích cho nội bộ/Orchestrator)
 * GET /api/users/:id
 */
const getUserById = async (req, res) => {
    try {
        const userId = req.params.id;
        const user = await User.findByPk(userId, {
            attributes: { exclude: ['password'] }
        });

        if (!user) {
            return res.status(404).json({ message: 'Không tìm thấy người dùng' });
        }

        res.status(200).json(user);
    } catch (error) {
        console.error('Lỗi API lấy user theo ID:', error);
        res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
    }
};

/**
 * Lấy danh sách toàn bộ Users
 * GET /api/users
 */
const getAllUsers = async (req, res) => {
    try {
        const users = await User.findAll({
            attributes: { exclude: ['password'] }
        });

        res.status(200).json(users);
    } catch (error) {
        console.error('Lỗi API Lấy danh sách users:', error);
        res.status(500).json({ message: 'Lỗi máy chủ nội bộ' });
    }
};

module.exports = {
    register,
    login,
    getMe,
    getUserById,
    getAllUsers
};
