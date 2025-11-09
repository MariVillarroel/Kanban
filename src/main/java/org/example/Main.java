package org.example;
import org.example.model.Task;
import org.example.service.TaskService;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
     public static void main(String[] args) {
        TaskService service = new TaskService("src/main/resources/tasks.csv");

        // Crear nueva tarea
        Task nueva = new Task(null, "Organizar reunión", "Reunión Kanban semanal", "2025-12-01", "Por hacer", "user1", "2025-11-09T18:30", "2025-11-09T18:30");
        service.add(nueva);

        // Listar todas las tareas
        List<Task> lista = service.readAll();
        for (Task t : lista) System.out.println(t.getId() + ": " + t.getTitulo());
    }
}