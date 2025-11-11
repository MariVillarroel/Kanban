package org.example.tareas.service;

import org.example.tareas.model.Task;
import org.example.tareas.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AssignTaskService {

    private static final Logger logger = Logger.getLogger(AssignTaskService.class.getName());

    @Autowired
    private TaskRepository taskRepository;

    /**
     * Asigna un responsable a una tarea, registrando quién la asignó y cuándo.
     *
     * @param taskId ID de la tarea
     * @param nuevoResponsableId ID del usuario responsable
     * @param asignadoPorId ID del usuario que realiza la asignación
     */
    @Transactional
    public Task asignarResponsable(Long taskId, Long nuevoResponsableId, Long asignadoPorId) {
        Optional<Task> optTask = taskRepository.findById(taskId);

        if (optTask.isEmpty()) {
            throw new IllegalArgumentException("La tarea con ID " + taskId + " no existe.");
        }

        Task tarea = optTask.get();

        // Validar que esté asignable
        if (!"asignable".equalsIgnoreCase(tarea.getEstado())) {
            throw new IllegalStateException("La tarea no se encuentra en estado asignable.");
        }

        tarea.setResponsableId(nuevoResponsableId);
        tarea.setAsignadoPor(asignadoPorId);
        tarea.setFechaAsignacion(LocalDateTime.now());
        tarea.setEstado("en progreso");

        Task tareaActualizada = taskRepository.save(tarea);

        logger.info("Tarea " + tarea.getId() +
                " asignada a usuario " + nuevoResponsableId +
                " por " + asignadoPorId);

        return tareaActualizada;
    }
}
