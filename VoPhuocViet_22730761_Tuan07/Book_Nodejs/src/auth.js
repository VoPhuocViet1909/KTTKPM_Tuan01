const bcrypt = require("bcryptjs");
const { readUsers, writeUsers } = require("./store");
const { publishUserRegistered } = require("./broker");

function validateRegisterInput(body) {
  const { name, email, password } = body || {};

  if (!name || !email || !password) {
    return "name, email, password la bat buoc";
  }

  if (typeof email !== "string" || !email.includes("@")) {
    return "email khong hop le";
  }

  if (typeof password !== "string" || password.length < 6) {
    return "password toi thieu 6 ky tu";
  }

  return null;
}

function validateLoginInput(body) {
  const { email, password } = body || {};

  if (!email || !password) {
    return "email, password la bat buoc";
  }

  return null;
}

function sanitizeUser(user) {
  return {
    id: user.id,
    name: user.name,
    email: user.email,
    createdAt: user.createdAt,
  };
}

async function register(req, res) {
  const error = validateRegisterInput(req.body);
  if (error) {
    return res.status(400).json({ message: error });
  }

  const { name, email, password } = req.body;
  const normalizedEmail = String(email).trim().toLowerCase();

  const users = readUsers();
  const existed = users.find((u) => u.email === normalizedEmail);
  if (existed) {
    return res.status(409).json({ message: "email da ton tai" });
  }

  const passwordHash = await bcrypt.hash(password, 10);
  const user = {
    id: Date.now().toString(),
    name: String(name).trim(),
    email: normalizedEmail,
    passwordHash,
    createdAt: new Date().toISOString(),
  };

  users.push(user);
  writeUsers(users);

  await publishUserRegistered({
    event: "USER_REGISTERED",
    user: sanitizeUser(user),
  });

  return res.status(201).json({
    message: "register thanh cong",
    user: sanitizeUser(user),
  });
}

async function login(req, res) {
  const error = validateLoginInput(req.body);
  if (error) {
    return res.status(400).json({ message: error });
  }

  const { email, password } = req.body;
  const normalizedEmail = String(email).trim().toLowerCase();

  const users = readUsers();
  const user = users.find((u) => u.email === normalizedEmail);

  if (!user) {
    return res.status(401).json({ message: "email hoac password khong dung" });
  }

  const matched = await bcrypt.compare(password, user.passwordHash);
  if (!matched) {
    return res.status(401).json({ message: "email hoac password khong dung" });
  }

  return res.status(200).json({
    message: "login thanh cong",
    user: sanitizeUser(user),
  });
}

module.exports = {
  register,
  login,
};
