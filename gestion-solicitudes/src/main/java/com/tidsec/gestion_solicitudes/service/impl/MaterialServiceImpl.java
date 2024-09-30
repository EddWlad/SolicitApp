package com.tidsec.gestion_solicitudes.service.impl;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.repositories.IMaterialRepository;
import com.tidsec.gestion_solicitudes.service.IMaterialService;

@Service
public class MaterialServiceImpl implements IMaterialService{

	@Autowired
	private IMaterialRepository materialRepository;
	
	@Override
	public List<Material> getAll() {
		return materialRepository.findByStatusNot(0);
	}

	@Override
	public Material getById(Long id) {
		return materialRepository.findById(id).orElse(null);
	}

	@Override
	public Material saveMaterial(Material materials) {
		return materialRepository.save(materials);
	}

	@Override
	public Material updateMaterials(Long id, Material materials) {
		Material materialDb = materialRepository.findById(id).orElse(null);
		if(materials != null)
		{
			materialDb.setName(materials.getName());
			materialDb.setDescription(materials.getDescription());
			materialDb.setInventories(materials.getInventories());
			materialDb.setUnitType(materials.getUnitType());
			materialDb.setStatus(materials.getStatus());
			return materialRepository.save(materialDb);
		}
		else
		{
			return null;
		}
		
	}

	@Override
	public boolean deleteMaterials(Long id) {
		Material materialDb = materialRepository.findById(id).orElse(null);
		if(materialDb  != null)
		{
			materialDb .setStatus(0);
			materialRepository.save(materialDb);
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public Long countMaterials() {
		return materialRepository.count();
	}

	@Override
	public Material findByName(String name) {
		return materialRepository.findByName(name);
	}

	@Override
	public List<Material> buscarPorIds(List<Long> ids) {
		return materialRepository.findAllById(ids);
	}
}
