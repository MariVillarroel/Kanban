// Datos iniciales de tareas
const tasks = [
  { id: 1, titulo: "Organizar reunión", responsable: "Ana", estado: "todo" },
  {
    id: 2,
    titulo: "Desarrollar login",
    responsable: "Luis",
    estado: "inprogress",
  },
  { id: 3, titulo: "Revisar Kanban docs", responsable: "", estado: "todo" },
  { id: 4, titulo: "Diseñar Dashboard", responsable: "Pedro", estado: "done" },
];

let tareaAEditar = null;

function renderBoard() {
  ["todo", "inprogress", "done"].forEach((col) => {
    const tasksDiv = document.querySelector(`#${col} .tasks`);
    tasksDiv.innerHTML = "";
    tasks
      .filter((t) => t.estado === col)
      .forEach((task) => {
        const el = makeTaskElem(task);
        tasksDiv.appendChild(el);
      });
  });
}

function makeTaskElem(task) {
  const div = document.createElement("div");
  div.className = "task";
  div.dataset.id = task.id;

  // Info
  const info = document.createElement("span");
  info.className = "info";
  info.textContent = task.titulo;

  // Responsable
  const responsable = document.createElement("span");
  responsable.className = "responsable";
  responsable.textContent = task.responsable
    ? `- ${task.responsable}`
    : "- Sin asignar";

  // Botón asignar
  const btn = document.createElement("button");
  btn.className = "asignar";
  btn.textContent = "Asignar";
  btn.onclick = () => abrirModalAsignar(task.id);

  div.appendChild(info);
  div.appendChild(responsable);
  div.appendChild(btn);
  return div;
}

// Modal de asignar responsable
function abrirModalAsignar(taskId) {
  tareaAEditar = tasks.find((t) => t.id === taskId);
  document.getElementById("inputResponsable").value =
    tareaAEditar.responsable || "";
  document.getElementById("modal").style.display = "block";
}
document.getElementById("closeModal").onclick = () => {
  document.getElementById("modal").style.display = "none";
};
document.getElementById("saveResponsableBtn").onclick = () => {
  const nombre = document.getElementById("inputResponsable").value.trim();
  if (!nombre) {
    alert("Debes ingresar un nombre de responsable.");
    return;
  }
  asignarResponsable(tareaAEditar.id, nombre);
  document.getElementById("modal").style.display = "none";
};

function asignarResponsable(taskId, responsable) {
  const task = tasks.find((t) => t.id === taskId);
  if (task) {
    task.responsable = responsable;
    renderBoard();
    // Resalta visual en la tarea modificada
    setTimeout(() => {
      const el = document.querySelector('.task[data-id="' + taskId + '"]');
      if (el) {
        el.classList.add("highlight");
        setTimeout(() => el.classList.remove("highlight"), 1500);
      }
    }, 40);
  }
}

renderBoard();
