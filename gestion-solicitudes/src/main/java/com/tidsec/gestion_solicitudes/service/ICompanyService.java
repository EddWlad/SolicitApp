package com.tidsec.gestion_solicitudes.service;

import java.util.List;
import java.util.Optional;

import com.tidsec.gestion_solicitudes.entities.Company;



public interface ICompanyService {
	List<Company> getAll();
	Company getById(Long id);
	Optional<Company> findById(Long id);
	Company saveCompany(Company company);
	Company updateCompany(Long id, Company company);
	public boolean deleteCompany(Long id);
	Long countCompany();
	
	Optional<Company> findByName(String name);
	Company findByRuc(String ruc);
}
