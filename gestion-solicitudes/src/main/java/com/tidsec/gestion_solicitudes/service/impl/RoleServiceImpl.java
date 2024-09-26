package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Role;
import com.tidsec.gestion_solicitudes.repositories.IRoleRepository;
import com.tidsec.gestion_solicitudes.service.IRoleService;

@Service
public class RoleServiceImpl implements IRoleService {
	
	@Autowired
	private IRoleRepository roleRepository;

	@Override
	public List<Role> getAll() {
		return roleRepository.findAllByStatusNot(0);
	}

	@Override
	public Role obtenerRole(Long id) {
		return roleRepository.findById(id).orElse(null);
	}

	@Override
	public Role saveRole(Role rol) {
		return roleRepository.save(rol);
	}

	@Override
	public Role updateRole(Long id, Role rol) {
		Role roleDb = roleRepository.findById(id).orElse(null);
		if (roleDb != null) {
			roleDb.setName(rol.getName());
			roleDb.setDescription(rol.getDescription());
			roleDb.setStatus(rol.getStatus());

			return roleRepository.save(roleDb);
		} else {
			return null;
		}
	}

	@Override
	public boolean deleteRole(Long id) {
		Role roleDb = roleRepository.findById(id).orElse(null);
		if (roleDb != null && roleDb.getName() != "Administrador") {
			roleDb.setStatus(0);
			roleRepository.save(roleDb);
			return true;
		} else {
			return false;
		}
	}

	@Override
	public Role findByName(String name) {
		return roleRepository.findByName(name);
	}

}
