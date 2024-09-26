package com.tidsec.gestion_solicitudes.service;

import java.util.List;


import com.tidsec.gestion_solicitudes.entities.Role;

public interface IRoleService {
	List<Role> getAll();
	Role obtenerRole(Long id);
	Role saveRole(Role rol);
	Role updateRole(Long id, Role rol);
	boolean deleteRole(Long id);
	
	Role findByName(String name);
}
