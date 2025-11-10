// import mongoose from "mongoose";
const mongoose = require("mongoose");

const PersonSchema = new mongoose.Schema({
  name: { type: String, required: true },
  email: { type: String, required: true, unique: true },
  password: { type: String, required: true },
  otp: { type: String },
  otpExpiry: { type: String },
  isVerified: { type: Boolean, default: false },
  role: { type: String, required: true, enum: ["leader", "member"] },
});

const User = mongoose.model("Person", PersonSchema);

module.exports = User;
