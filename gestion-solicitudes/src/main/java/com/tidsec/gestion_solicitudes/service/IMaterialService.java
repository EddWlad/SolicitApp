package com.tidsec.gestion_solicitudes.service;

import java.util.List;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;

public interface IMaterialService {
	List<Material> getAll();
	Material getById(Long id);
	Material saveMaterial(Material materials);
	Material updateMaterials(Long id, Material materials);
	boolean deleteMaterials(Long id);
	Long countMaterials();
	
	List<Material> findByInventory(Inventory inventory);
	
	Material findByName(String name);
}
