package com.tidsec.gestion_solicitudes.repositories;


import com.tidsec.gestion_solicitudes.entities.Project;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IProjectRepository extends JpaRepository<Project, Long> {
	Project projectName( String projectName);
	List<Project> findAllByStatusNot(Integer status);
	Optional<Project> findByEngineer_Name(String engineerName);
}
