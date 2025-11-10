package com.kanban.domain;

import jakarta.persistence.*;

@Entity @Table(name="tasks")
public class Task {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String title;
  private Long projectId;
  private Long assigneeId;

  public Long getId(){return id;}
  public String getTitle(){return title;}
  public void setTitle(String t){this.title=t;}
  public Long getProjectId(){return projectId;}
  public void setProjectId(Long p){this.projectId=p;}
  public Long getAssigneeId(){return assigneeId;}
  public void setAssigneeId(Long a){this.assigneeId=a;}
}
