package com.tidsec.gestion_solicitudes.service;

import java.util.List;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.Request;
import com.tidsec.gestion_solicitudes.entities.User;


public interface IRequestService {
	
	List<Request> getAll();
	Request getById(Long id);
	Request saveRequest(Request request);
	Request updateRequest(Long id, Request request);
	public boolean deleteRequest(Long id);
	Long countRequest();
	
	List<Request> findByCompany(Company company);
	List<Request> findByProject(Project project);
	List<Request> findByRequester(User requester);

}
