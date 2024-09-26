package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.repositories.IUserRepository;
import com.tidsec.gestion_solicitudes.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	
	@Autowired
	private IUserRepository userRepository;

	@Override
	public List<User> getAll() {
		return userRepository.findAllByStatusNot(0);
	}

	@Override
	public User getUser(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public User saveUser(User user) {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(Long id, User user) {
		User userDb = userRepository.findById(id).orElse(null);
		if (user != null) {
			userDb.setName(user.getName());
			userDb.setLastName(user.getLastName());
			userDb.setIdentification(user.getIdentification());
			userDb.setEmail(user.getEmail());
			userDb.setUsername(user.getUsername());
			//userDb.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
			userDb.setPassword(user.getPassword());
			userDb.setRole(user.getRole());
			userDb.setStatus(user.getStatus());

			return userRepository.save(userDb);
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteUser(Long id) {
		User UserDb = userRepository.findById(id).orElse(null);
		if (UserDb != null) {
			UserDb.setStatus(0);
			userRepository.save(UserDb);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public User findByIdentification(String identification) {
		return userRepository.findByIdentification(identification).orElse(null);
	}

	@Override
	public List<User> findByRole(String role) {
		return userRepository.findByRole_Name(role);
	}

	@Override
	public Optional<User> findById(Long id) {
		return userRepository.findById(id);
	}

}
