const express = require("express");
const connectDB = require("./config/db");
const session = require("express-session");
const app = express();

connectDB();

//middlewares:
// /formato json
app.use(express.json());

// /configuracion de sesion
app.use(
  session({
    secret: "theSuperSecretKey",
    resave: false,
    saveUninitialized: false,
    cookie: { secure: false },
  })
);
// app.get("/", (req, res) => {
//   res.send("Hello from the server");
// });

//campos protegidos cuando no se ha iniciado sesion
const authRoutes = require("./routes/authRoutes");

app.use("/api/auth", authRoutes);

app.listen(8000, () => {
  console.log("Server is listening...");
});
