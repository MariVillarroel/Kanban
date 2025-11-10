const express = require("express");
const {
  member,
  login,
  register,
  dashboard,
  logout,
  resendOTP,
  VerifyOTP,
} = require("../controllers/authController");
const authmiddleware = require("../middleware/authmiddleware");
const roleMiddleware = require("../middleware/roleMiddleware");
const router = express.Router();

router.post("/login", login);
router.post("/register", register);
router.post("/logout", logout);
router.post("/resendOTP", resendOTP);
router.post("/verifyOTP", VerifyOTP);

//Panel de lideres
router.get("/dashboard", authmiddleware, roleMiddleware("leader"), dashboard);

//panel de miembros comunes
router.get("/member", authmiddleware, roleMiddleware("member"), member);
module.exports = router;
