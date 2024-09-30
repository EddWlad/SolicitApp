package com.tidsec.gestion_solicitudes.service;

import java.util.List;
import java.util.Optional;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;

public interface IInventoryService {
	List<Inventory> getAll();
	Inventory getById(Long id);
	Inventory findById(Long id);
	Inventory saveInventory(Inventory inventory);
	Inventory updateInventory(Long id, Inventory inventory);
	boolean deleteInventory(Long id);
	Long countInventory();
	
	//List<Inventory> findByProject(Project project);
}
