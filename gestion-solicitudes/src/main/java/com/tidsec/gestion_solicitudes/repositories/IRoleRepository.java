package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tidsec.gestion_solicitudes.entities.Role;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long>{
	Role findByName(String name);
	List<Role> findAllByStatusNot(Integer status);
}
