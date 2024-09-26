package com.tidsec.gestion_solicitudes.service;

import java.util.List;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.entities.Project;

public interface IInventoryService {
	List<Inventory> getAll();
	Inventory getById(Long id);
	Inventory saveInventory(Inventory inventory);
	Inventory updateInventory(Long id, Inventory inventory);
	boolean deleteInventory(Long id);
	Long countInventory();
	
	//List<Inventory> findByProject(Project project);
	List<Inventory> findByMaterial(Material material);
}
