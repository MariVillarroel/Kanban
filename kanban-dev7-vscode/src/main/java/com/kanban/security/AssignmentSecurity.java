package com.kanban.security;

import com.kanban.domain.*;
import com.kanban.exception.*;
import com.kanban.repository.*;
import org.springframework.stereotype.Component;

@Component
public class AssignmentSecurity {
  private final TaskRepository taskRepo;
  private final ProjectRepository projectRepo;

  public AssignmentSecurity(TaskRepository t, ProjectRepository p){
    this.taskRepo = t; this.projectRepo = p;
  }

  /** Un líder solo asigna dentro de su proyecto */
  public void checkLeaderAssignment(User leader, Long taskId, Long targetProjectId){
    if (leader == null || !leader.isActive()) throw new UserInactiveException();
    if (!leader.isLeader()) throw new NoPermissionException();

    Task task = taskRepo.findById(taskId).orElseThrow(TaskNotFoundException::new);
    Project leaderProject = projectRepo.findById(leader.getProjectId())
      .orElseThrow(NoPermissionException::new);

    if (!leaderProject.getId().equals(task.getProjectId()) ||
        !leaderProject.getId().equals(targetProjectId)) {
      throw new NoPermissionException();
    }
  }
}
