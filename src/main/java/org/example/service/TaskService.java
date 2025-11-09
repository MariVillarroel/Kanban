package org.example.service;

import org.example.model.Task;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class TaskService {
    private static final String[] HEADERS = { "id", "titulo", "descripcion", "fechaLimite", "estado", "creador", "createdAt", "updatedAt" };
    private final Path filePath;

    public TaskService(String csvPath) {
        this.filePath = Paths.get(csvPath);
    }

    public List<Task> readAll() {
        List<Task> tasks = new ArrayList<>();
        if (!Files.exists(filePath)) return tasks;
        try (Reader r = Files.newBufferedReader(filePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withHeader(HEADERS)
                    .withFirstRecordAsHeader()
                    .parse(r);
            for (CSVRecord rec : records) {
                tasks.add(fromRecord(rec));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tasks;
    }

    public void writeAll(List<Task> tasks) {
        try (Writer w = Files.newBufferedWriter(filePath)) {
            CSVPrinter printer = new CSVPrinter(w, CSVFormat.DEFAULT.withHeader(HEADERS));
            for (Task t : tasks) printer.printRecord(
                t.getId(), t.getTitulo(), t.getDescripcion(), t.getFechaLimite(), t.getEstado(),
                t.getCreador(), t.getCreatedAt(), t.getUpdatedAt()
            );
            printer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(Task task) {
        List<Task> tasks = readAll();
        Long nextId = tasks.stream().mapToLong(t -> t.getId()).max().orElse(0L) + 1;
        task.setId(nextId);
        tasks.add(task);
        writeAll(tasks);
    }

    public Optional<Task> findById(Long id) {
        return readAll().stream().filter(t -> t.getId().equals(id)).findFirst();
    }

    public void update(Task updatedTask) {
        List<Task> tasks = readAll();
        for (int i = 0; i < tasks.size(); i++) {
            if (tasks.get(i).getId().equals(updatedTask.getId())) {
                tasks.set(i, updatedTask);
                break;
            }
        }
        writeAll(tasks);
    }

    public void delete(Long id) {
        List<Task> tasks = readAll();
        tasks.removeIf(t -> t.getId().equals(id));
        writeAll(tasks);
    }

    private Task fromRecord(CSVRecord rec) {
        return new Task(
            Long.parseLong(rec.get("id")),
            rec.get("titulo"),
            rec.get("descripcion"),
            rec.get("fechaLimite"),
            rec.get("estado"),
            rec.get("creador"),
            rec.get("createdAt"),
            rec.get("updatedAt")
        );
    }
}
