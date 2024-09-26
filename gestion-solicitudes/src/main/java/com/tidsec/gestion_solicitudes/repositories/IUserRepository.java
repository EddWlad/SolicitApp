package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tidsec.gestion_solicitudes.entities.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
	Optional<User> findByEmail( String email);
	List<User> findAllByStatusNot(Integer status);
	Optional<User> findByIdentification(String identification);
	List<User> findByRole_Name(String roleName);
}
