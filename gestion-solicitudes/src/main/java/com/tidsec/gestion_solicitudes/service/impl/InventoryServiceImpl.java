package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.repositories.IInventoryRepository;
import com.tidsec.gestion_solicitudes.service.IInventoryService;

@Service
public class InventoryServiceImpl implements IInventoryService {
	
	@Autowired
	private IInventoryRepository inventoryRepository;

	@Override
	public List<Inventory> getAll() {
		return inventoryRepository.findByStatusNot(0);
	}

	@Override
	public Inventory getById(Long id) {
		return inventoryRepository.findById(id).orElse(null);
	}

	@Override
	public Inventory saveInventory(Inventory inventory) {
		return inventoryRepository.save(inventory);
	}

	@Override
	public Inventory updateInventory(Long id, Inventory inventory) {
		Inventory inventoryDb = inventoryRepository.findById(id).orElse(null);
		if(inventory != null)
		{
			inventoryDb.setQuantity(inventory.getQuantity());
			inventoryDb.setRequest(inventory.getRequest());
			inventoryDb.setStatus(inventory.getStatus());
			return inventoryRepository.save(inventoryDb );
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean deleteInventory(Long id) {
		Inventory inventoryDb = inventoryRepository.findById(id).orElse(null);
		if(inventoryDb  != null)
		{
			inventoryDb .setStatus(0);
			inventoryRepository.save(inventoryDb);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Long countInventory() {
		return inventoryRepository.count();
	}

	//@Override
	//public List<Inventory> findByProject(Project project) {
		//return inventoryRepository.findByProject(project);
	//}

	@Override
	public List<Inventory> findByMaterial(Material material) {
		return inventoryRepository.findByMaterial(material);
	}

}
