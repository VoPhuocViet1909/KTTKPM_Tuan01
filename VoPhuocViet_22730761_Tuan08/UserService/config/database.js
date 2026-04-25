const { Sequelize } = require('sequelize');
require('dotenv').config();

// Khởi tạo Sequelize kết nối tới MySQL
const sequelize = new Sequelize(
  process.env.DB_NAME, 
  process.env.DB_USER, 
  process.env.DB_PASS, 
  {
    host: process.env.DB_HOST,
    dialect: 'mysql',
    logging: false, // Tắt log query để console gọn gàng hơn
  }
);

module.exports = sequelize;
