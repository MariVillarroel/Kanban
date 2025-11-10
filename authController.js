const User = require("../models/user");
const nodemailer = require("nodemailer");
const crypto = require("crypto");
const { response } = require("express");

const bcryptjs = require("bcryptjs");
const jwt = require("jsonwebtoken");

//Direccion de envio del email
const transporter = nodemailer.createTransport({
  service: "gmail",
  auth: {
    user: "correofuente@gmail.com",
    pass: "gmail app password",
  },
});

//generar OTP
const generateOTP = () => crypto.randomInt(100000, 999999).toString();

//registrar usuario y enviar OTP
exports.register = async (req, res) => {
  try {
    //registrar usuario
    const { name, email, password, role } = req.body;
    let user = await User.findOne({ email });

    if (user) return res.status(400).json({ response: "El usuario ya existe" });

    const otp = generateOTP();
    const otpExpiry = new Date(Date.now() + 10 * 60 * 1000);

    user = new User({ name, email, password, otp, otpExpiry, role });
    await user.save();

    //enviar OTP
    await transporter.sendMail({
      from: "correofuente@gmail.com",
      to: email,
      subject: "Verificacion OTP",
      text: `Tu OTP es: ${otp}`,
    });

    res.status(200).json({
      response:
        "Usuario registrado exitosamente, por favor verifica tu OTP enviado a tu gmail",
    });
  } catch (err) {
    res.status(500).json({ message: "Error al registrar usuario, ", err });
  }
};

//verificar OTP
exports.VerifyOTP = async (req, res) => {
  try {
    const { email, otp } = req.body;

    const user = await User.findOne({ email });
    if (!user)
      return res.status(400).json({ error: "Email de usuario no encontrado" });
    if (user.isVerified)
      return res.status(400).json({ message: "Usuario ya verificado" });
    if (otp !== user.otp || user.otpExpiry < new Date())
      return res
        .status(400)
        .json({ error: "la OTP ingresada es incorrecta o ya expiró" });

    user.isVerified = true;
    user.otp = undefined;
    user.otpExpiry = undefined;
    await user.save();

    res.json({
      message:
        "Verificacion de Email terminada, puedes iniciar sesion nuevamente",
    });
  } catch (err) {
    res.status(500).json({ error: "Error al verificar OTP, ", err });
  }
};

//reenviar OTP
exports.resendOTP = async (req, res) => {
  try {
    const { email } = req.body;
    const user = await User.findOne({ email });
    if (!user)
      return res.status(400).json({
        error: "email no registrado",
      });

    const otp = generateOTP();
    user.otp = otp;
    user.otpExpiry = new Date(Date.now() + 10 * 60 * 1000);

    await user.save();

    await transporter.sendMail({
      from: "correofuente@gmail.com",
      to: email,
      subject: "Reenvio de  OTP",
      text: `Tu OTP reenviada es: ${otp}`,
    });

    res.json({ message: "Se ha reenviado el OTP a tu correo" });
  } catch (err) {
    res.status(400).json({ error: "Reenvio de OTP fallida, ", err });
  }
};

//simulacion de un dashboard con sesion activa
exports.dashboard = async (req, res) => {
  res.json({
    message: `Bienvenido al panel principal de lider, ${req.session.user.name}`,
  });
};

exports.member = async (req, res) => {
  res.json({
    meessage: `Bienvenido al panel de usuario, ${req.session.user.name}`,
  });
};

//logout
exports.logout = (req, res) => {
  req.session.destroy((err) => {
    if (err) res.status(400).json({ error: "Error Login out, ", err });
    res.json({ message: "Sesion cerrada exitosamente" });
  });
};
//iniciar sesion
exports.login = async (req, res) => {
  try {
    const { email, password } = req.body;
    const user = await User.findOne({ email });

    if (!user)
      return res.status(400).json({
        error: "El usuario no existe, por favor intenta registrarte primero",
      });

    if (user.password !== password)
      return res.status(400).json({ error: "password incorrecta" });

    req.session.user = {
      id: user._id,
      name: user.name,
      email: user.email,
      role: user.role,
    };
    res.json({ message: "inicio de sesion exitoso" });
  } catch (err) {
    res.json({ error: "error al iniciar sesion, ", err });
  }
};
