package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tidsec.gestion_solicitudes.entities.Company;


@Repository
public interface ICompanyRepository extends JpaRepository<Company, Long>{
	List<Company> findByStatusNot(Integer status);
	Optional<Company> findByName(String name);
	Optional<Company> findByRuc(String ruc);
}
