package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.entities.Project;


public interface IInventoryRepository extends JpaRepository<Inventory, Long> {
	List<Inventory> findByStatusNot(Integer status);
	//List<Inventory> findByProject(Project project);
	List<Inventory> findByMaterial(Material material);
}
