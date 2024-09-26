package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.repositories.ICompanyRepository;
import com.tidsec.gestion_solicitudes.service.ICompanyService;

@Service
public class CompanyServiceImpl implements ICompanyService {
	
	@Autowired
	ICompanyRepository companyRepository;

	@Override
	public List<Company> getAll() {
		return companyRepository.findByStatusNot(0);
	}

	@Override
	public Company getById(Long id) {
		return companyRepository.findById(id).orElse(null);
	}

	@Override
	public Optional<Company> findById(Long id) {
		return companyRepository.findById(id);
	}

	@Override
	public Company saveCompany(Company company) {
		return companyRepository.save(company);
	}

	@Override
	public Company updateCompany(Long id, Company company) {
		Company companyDb = companyRepository.findById(id).orElse(null);
		if(company != null)
		{
			companyDb.setRuc(company.getRuc());
			companyDb.setName(company.getName());
			companyDb.setPhone(company.getPhone());
			companyDb.setImage(company.getImage());
			companyDb.setStatus(company.getStatus());
			return companyRepository.save(companyDb);
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public boolean deleteCompany(Long id) {
		Company companyDb = companyRepository.findById(id).orElse(null);
		if(companyDb != null)
		{
			companyDb.setStatus(0);
			companyRepository.save(companyDb);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Long countCompany() {
		return companyRepository.count();
	}

	@Override
	public Optional<Company> findByName(String name) {
		return companyRepository.findByName(name);
	}

	@Override
	public Company findByRuc(String ruc) {
		return companyRepository.findByRuc(ruc).orElse(null);
	}

}
