module.exports = (req, res, next) => {
  if (!req.session.user) {
    res
      .status(400)
      .json({ message: "Inicia sesion o registrate para entrar al sitio" });
  }

  next();
};
