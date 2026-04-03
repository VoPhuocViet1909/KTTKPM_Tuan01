# STAGE 1: Build
FROM node:18 AS builder
WORKDIR /app
COPY package.json .
RUN npm install
COPY . .
# Giả sử ở đây bạn có bước build (như npm run build), nhưng với app thuần này ta bỏ qua.

# STAGE 2: Run (Sử dụng node:18-alpine siêu nhẹ)
FROM node:18-alpine
WORKDIR /app

# CHỈ copy những gì cần thiết từ stage "builder" sang
COPY --from=builder /app/package.json .
COPY --from=builder /app/app.js .
COPY --from=builder /app/node_modules ./node_modules

EXPOSE 3000
CMD ["node", "app.js"]