const form = document.getElementById("formSignup");

const nombre = document.getElementById("nombre");
const correo = document.getElementById("correo");
const password = document.getElementById("password");
const passwordConfirm = document.getElementById("passwordConfirm");

const nombreError = document.getElementById("nombreError");
const correoError = document.getElementById("correoError");
const passwordError = document.getElementById("passwordError");
const passwordConfirmError = document.getElementById("passwordConfirmError");
const apiError = document.getElementById("apiError");

nombre.addEventListener("input", () => {
  if (nombre.value.trim().length < 3) {
    nombreError.textContent = "El nombre debe tener al menos 3 caracteres.";
  } else {
    nombreError.textContent = "";
  }
});

correo.addEventListener("input", () => {
  if (!correo.validity.valid) {
    correoError.textContent = "Introduce un correo válido.";
  } else {
    correoError.textContent = "";
  }
});

password.addEventListener("input", () => {
  if (password.value.length < 6) {
    passwordError.textContent = "La contraseña debe tener mínimo 6 caracteres.";
  } else {
    passwordError.textContent = "";
  }
});

passwordConfirm.addEventListener("input", () => {
  if (passwordConfirm.value !== password.value) {
    passwordConfirmError.textContent = "Las contraseñas no coinciden.";
  } else {
    passwordConfirmError.textContent = "";
  }
});

form.addEventListener("submit", (event) => {
  event.preventDefault();
  apiError.textContent = "";

  // Validar todos los campos al enviar
  if (nombre.value.trim().length < 3) {
    nombreError.textContent = "El nombre debe tener al menos 3 caracteres.";
    return;
  }
  if (!correo.validity.valid) {
    correoError.textContent = "Introduce un correo válido.";
    return;
  }
  if (password.value.length < 6) {
    passwordError.textContent = "La contraseña debe tener mínimo 6 caracteres.";
    return;
  }
  if (passwordConfirm.value !== password.value) {
    passwordConfirmError.textContent = "Las contraseñas no coinciden.";
    return;
  }

  // Simulación de envío a backend (reemplaza con tu lógica fetch)
  setTimeout(() => {
    alert("¡Registro exitoso! Ahora puedes iniciar sesión.");
    form.reset();
  }, 800);
});
