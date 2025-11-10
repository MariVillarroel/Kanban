package com.kanban.domain;

import jakarta.persistence.*;

@Entity @Table(name="users")
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String email;
  private String name;
  private boolean active = true;
  private String role = "MEMBER"; // MEMBER / LEADER / ADMIN
  private Long projectId;

  public Long getId(){return id;}
  public String getEmail(){return email;}
  public void setEmail(String e){this.email=e;}
  public String getName(){return name;}
  public void setName(String n){this.name=n;}
  public boolean isActive(){return active;}
  public void setActive(boolean a){this.active=a;}
  public String getRole(){return role;}
  public void setRole(String r){this.role=r;}
  public Long getProjectId(){return projectId;}
  public void setProjectId(Long p){this.projectId=p;}

  public boolean isLeader(){ return "LEADER".equalsIgnoreCase(role) || "ADMIN".equalsIgnoreCase(role); }
}
