# User Auth API (Node.js)

API dang ky/dang nhap de Frontend ket noi trong LAN.

## 1) Cai dat

```bash
npm install
```

## 2) Cau hinh

Tao file `.env` (co the copy tu `.env.example`):

```env
PORT=8081
HOST=0.0.0.0
AMQP_URL=amqp://localhost:5672
AMQP_EXCHANGE=movie.events
AMQP_ROUTING_KEY=USER_REGISTERED
```

`HOST=0.0.0.0` de may khac trong LAN goi duoc API.

## 3) Chay server

```bash
npm run start
```

Local URL:

- `http://localhost:8081`

LAN URL (frontend o may khac):

- `http://<IP-MAY-CHAY-API>:8081`
- Vi du: `http://192.168.1.10:8081`

## 4) API cho FE

### Health check

- `GET /health`

Response 200:

```json
{
  "service": "user-auth-service",
  "status": "ok"
}
```

### Dang ky

- `POST /register`
- Body JSON:

```json
{
  "name": "Nguyen Van A",
  "email": "a@gmail.com",
  "password": "123456"
}
```

Response 201:

```json
{
  "message": "register thanh cong",
  "user": {
    "id": "...",
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "createdAt": "2026-04-10T12:00:35.540Z"
  }
}
```

Loi thuong gap:

- 400: thieu field hoac password < 6
- 409: email da ton tai

Sau khi dang ky thanh cong, service se publish event `USER_REGISTERED` qua RabbitMQ.
Neu RabbitMQ chua chay, API van tra ket qua dang ky binh thuong (chi log canh bao).

### Dang nhap

- `POST /login`
- Body JSON:

```json
{
  "email": "a@gmail.com",
  "password": "123456"
}
```

Response 200:

```json
{
  "message": "login thanh cong",
  "user": {
    "id": "...",
    "name": "Nguyen Van A",
    "email": "a@gmail.com",
    "createdAt": "2026-04-10T12:00:35.540Z"
  }
}
```

Loi thuong gap:

- 400: thieu email/password
- 401: sai email hoac password

## 5) Du lieu user

User duoc luu tai `data/users.json`.
