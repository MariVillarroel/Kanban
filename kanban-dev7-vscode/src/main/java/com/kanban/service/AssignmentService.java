package com.kanban.service;

import com.kanban.domain.User;
import com.kanban.domain.Task;
import com.kanban.repository.TaskRepository;
import com.kanban.security.AssignmentSecurity;
import org.springframework.stereotype.Service;

@Service
public class AssignmentService {
  private final TaskRepository taskRepo;
  private final AssignmentSecurity guard;

  public AssignmentService(TaskRepository t, AssignmentSecurity g){
    this.taskRepo=t; this.guard=g;
  }

  public void assignTask(User leader, Long taskId, Long targetProjectId, Long assigneeId){
    guard.checkLeaderAssignment(leader, taskId, targetProjectId);
    Task task = taskRepo.findById(taskId).orElseThrow();
    task.setAssigneeId(assigneeId);
    taskRepo.save(task);
  }
}
