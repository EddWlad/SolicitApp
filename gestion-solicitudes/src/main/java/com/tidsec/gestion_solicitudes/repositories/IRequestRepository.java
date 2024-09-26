package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.Request;
import com.tidsec.gestion_solicitudes.entities.User;


public interface IRequestRepository extends JpaRepository<Request, Long>{
	List<Request> findAllByStatusLogicalDelete(Integer statusLogicalDelete);
	List<Request> findByCompany(Company company);
	List<Request> findByProject(Project project);
	List<Request> findByRequester(User requester);

}
