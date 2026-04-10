require("dotenv").config();

const express = require("express");
const cors = require("cors");
const { register, login } = require("./auth");

const app = express();
const PORT = Number(process.env.PORT || 8081);
const HOST = process.env.HOST || "0.0.0.0";

app.use(cors());
app.use(express.json());

app.get("/", (req, res) => {
  res.status(200).json({
    service: "user-auth-service",
    message: "API is running",
    endpoints: ["/health", "/register", "/login"],
  });
});

app.get("/favicon.ico", (req, res) => {
  res.status(204).end();
});

app.get("/health", (req, res) => {
  res.status(200).json({
    service: "user-auth-service",
    status: "ok",
  });
});

app.post("/register", register);
app.post("/login", login);

app.use((req, res) => {
  res.status(404).json({ message: "not found" });
});

app.listen(PORT, HOST, () => {
  console.log(`Auth API running at http://${HOST}:${PORT}`);
});
