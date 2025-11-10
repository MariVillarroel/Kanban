module.exports = (...authorizedRoles) => {
  return (req, res, next) => {
    if (!authorizedRoles.includes(req.session.user.role)) {
      res.status(403).json({ message: "Acceso denegado" });
    }
    next();
  };
};
