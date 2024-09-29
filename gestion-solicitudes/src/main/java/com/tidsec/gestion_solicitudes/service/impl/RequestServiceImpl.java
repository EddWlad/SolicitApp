package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.Request;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.model.RequestDTO;
import com.tidsec.gestion_solicitudes.model.RequestModalDTO;
import com.tidsec.gestion_solicitudes.repositories.IRequestRepository;
import com.tidsec.gestion_solicitudes.repositories.IRequestRepositoryCustom;
import com.tidsec.gestion_solicitudes.service.IRequestService;

@Service
public class RequestServiceImpl implements IRequestService{

	@Autowired
	private IRequestRepository requestRepository;
	
	@Autowired IRequestRepositoryCustom requestRepositoryCustom;
	
	@Override
	public List<Request> getAll() {
		return requestRepository.findAllByStatusLogicalDelete(0) ;
	}

	@Override
	public Request getById(Long id) {
		return requestRepository.findById(id).orElse(null);
	}

	@Override
	public Request saveRequest(Request request) {
		return requestRepository.save(request);
	}

	@Override
	public Request updateRequest(Long id, Request request) {
		Request requestDb = requestRepository.findById(id).orElse(null);
		if(requestDb != null) 
		{
			requestDb.setCompany(request.getCompany());
			requestDb.setProject(request.getProject());
			requestDb.setDate(request.getDate());
			requestDb.setRequester(request.getRequester());
			requestDb.setInventory(request.getInventory());
			requestDb.setStatus(request.getStatus());
			requestDb.setStatusLogicalDelete(request.getStatusLogicalDelete());
			return requestRepository.save(requestDb);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean deleteRequest(Long id) {
		Request requestDb = requestRepository.findById(id).orElse(null);
		if(requestDb != null)
		{
			requestDb.setStatusLogicalDelete(0);
			requestRepository.save(requestDb);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Long countRequest() {
		return requestRepository.count();
	}

	@Override
	public List<Request> findByCompany(Company company) {
		return requestRepository.findByCompany(company);
	}

	@Override
	public List<Request> findByProject(Project project) {
		return requestRepository.findByProject(project);
	}

	@Override
	public List<Request> findByRequester(User requester) {
		return requestRepository.findByRequester(requester);
	}

	@Override
	public List<RequestModalDTO> detailRequestById(Long id) {
		//return requestRepositoryCustom.findDescriptionById(id);
		return null;
	}

	@Override
	public List<RequestDTO> listDetailRequest() {
		//return requestRepositoryCustom.findByDescription();
		return null;
	}

}
