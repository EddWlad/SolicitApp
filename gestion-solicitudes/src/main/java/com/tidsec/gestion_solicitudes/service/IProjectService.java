package com.tidsec.gestion_solicitudes.service;

import java.util.List;
import java.util.Optional;

import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.User;


public interface IProjectService {
	List<Project> getAll();
	Project getProject(Long id);
	Project saveProject(Project project);
	Project updateProject(Long id, Project project);
	boolean deleteProject(Long id);
	Project projectName(String name);
	Optional<Project> findById(Long id);
	Optional <Project> findByEngineer(String engineer);
	List<User> findByRole();
}
