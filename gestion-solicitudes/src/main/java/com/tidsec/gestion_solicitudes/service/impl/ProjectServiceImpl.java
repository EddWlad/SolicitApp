package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.repositories.IProjectRepository;
import com.tidsec.gestion_solicitudes.repositories.IUserRepository;
import com.tidsec.gestion_solicitudes.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService{

	@Autowired
	private IProjectRepository projectRepository;
	@Autowired
	private IUserRepository userRepository;
	
	@Override
	public List<Project> getAll() {
		return projectRepository.findAllByStatusNot(0);
	}

	@Override
	public Project getProject(Long id) {
		return projectRepository.findById(id).orElse(null);
	}

	@Override
	public Project saveProject(Project project) {
		return projectRepository.save(project);
	}

	@Override
	public Project updateProject(Long id, Project project) {
		Project projectDb = projectRepository.findById(id).orElse(null);
		if(project != null)
		{
			projectDb.setProjectName(project.getProjectName());
			projectDb.setObservations(project.getObservations());
			projectDb.setMaterialLiquidator(project.getMaterialLiquidator());
			projectDb.setContractor(project.getContractor());
			projectDb.setCity(project.getCity());
			projectDb.setStartDate(project.getStartDate());
			projectDb.setInventory(project.getInventory());
			projectDb.setEndDate(project.getEndDate());
			projectDb.setEngineer(project.getEngineer());
			projectDb.setStatus(project.getStatus());
			return projectRepository.save(projectDb);
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public boolean deleteProject(Long id) {
		Project projectDb = projectRepository.findById(id).orElse(null);
		if(projectDb  != null)
		{
			projectDb.setStatus(0);
			projectRepository.save(projectDb);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Project projectName(String name) {
		return projectRepository.projectName(name);
	}

	@Override
	public Optional <Project> findByEngineer(String engineer) {
		return projectRepository.findByEngineer_Name(engineer);
	}

	@Override
	public List<User> findByRole() {
		return userRepository.findByRole_Name("Ingeniero residente");
	}

	@Override
	public Optional<Project> findById(Long id) {
		return projectRepository.findById(id);
	}

}
