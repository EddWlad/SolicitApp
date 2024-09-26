package com.tidsec.gestion_solicitudes.service;

import java.util.List;
import java.util.Optional;

import com.tidsec.gestion_solicitudes.entities.User;



public interface IUserService {
	List<User> getAll();
	User getUser(Long id);
	Optional<User> findById(Long id);
	User saveUser(User user);
	User updateUser(Long id, User user);
	boolean deleteUser(Long id);
	User findByEmail(String email);
	User findByIdentification(String identification);
	List<User> findByRole(String role);
}
