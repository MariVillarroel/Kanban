package com.kanban.repository;
import com.kanban.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ProjectRepository extends JpaRepository<Project, Long>{}
