package com.tidsec.gestion_solicitudes.repositories;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;

@Repository
public interface IMaterialRepository extends JpaRepository<Material, Long> {
	List<Material> findByStatusNot(Integer status);
	List<Material> findByInventory(Inventory inventory);
	Material findByName(String name);
}
