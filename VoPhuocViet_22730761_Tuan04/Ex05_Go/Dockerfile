FROM golang:1.21-alpine

WORKDIR /app

COPY . .

# Thêm dòng này để khởi tạo module tự động bên trong container
RUN go mod init ex05-go-app && go build -o main .

EXPOSE 8080

CMD ["./main"]