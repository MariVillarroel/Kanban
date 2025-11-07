const form = document.getElementById("formLogin");
const correo = document.getElementById("correo");
const password = document.getElementById("password");
const correoError = document.getElementById("correoError");
const passwordError = document.getElementById("passwordError");
const apiError = document.getElementById("apiError");

// Valida en tiempo real correo y contraseña
correo.addEventListener("input", () => {
  if (!correo.validity.valid) {
    correoError.textContent = "Correo inválido.";
  } else {
    correoError.textContent = "";
  }
});

password.addEventListener("input", () => {
  if (password.value.length < 6) {
    passwordError.textContent = "Mínimo 6 caracteres.";
  } else {
    passwordError.textContent = "";
  }
});

form.addEventListener("submit", (e) => {
  e.preventDefault();
  apiError.textContent = "";
  // Validaciones otra vez antes de enviar
  if (!correo.validity.valid) {
    correoError.textContent = "Correo inválido.";
    return;
  }
  if (password.value.length < 6) {
    passwordError.textContent = "Mínimo 6 caracteres.";
    return;
  }
  // Ejemplo de "petición" a la API (aquí simulado)
  // Reemplaza por fetch si tienes backend
  setTimeout(() => {
    if (correo.value !== "test@email.com" || password.value !== "123456") {
      apiError.textContent = "Usuario o contraseña incorrectos.";
    } else {
      apiError.textContent = "";
      form.reset();
      alert("¡Login exitoso!");
    }
  }, 900);
});
