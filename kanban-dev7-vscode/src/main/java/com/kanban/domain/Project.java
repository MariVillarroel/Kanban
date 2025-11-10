package com.kanban.domain;

import jakarta.persistence.*;

@Entity @Table(name="projects")
public class Project {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String name;

  public Long getId(){return id;}
  public String getName(){return name;}
  public void setName(String n){this.name=n;}
}
