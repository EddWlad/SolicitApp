package com.tidsec.gestion_solicitudes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.service.IInventoryService;
import com.tidsec.gestion_solicitudes.service.IMaterialService;


import jakarta.validation.Valid;


@Controller
@RequestMapping("/materials")
public class MaterialController {

	@Autowired
	private IMaterialService service;
	
	@Autowired
	private IInventoryService inventoryService;
	
	@GetMapping
	public String listarEmpresas(Model model) {
		List<Material> material = service.getAll();
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String nombreUsuario = authentication.getName();
		//Usuarios usuario = usuariosService.findByCorreo(nombreUsuario);
		model.addAttribute("activoMaterial", "active");
		//model.addAttribute("nombreRol", usuario.getRoles().getNombre());
		//model.addAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());
		model.addAttribute("listMaterial", material);
		model.addAttribute("material", new Material());
		return "materials";
	}
	
	@PostMapping("/save")
	public String saveMaterial(@Valid @ModelAttribute Material material, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				Material nameValid = service.findByName(material.getName());
				if(nameValid==null) {
					Material materialSaved = service.saveMaterial(material);
					List<Inventory> inventory = inventoryService.findByMaterial(materialSaved);
					//materialSaved.setInventory(inventory);
					service.saveMaterial(material);
					redirectAttributes.addFlashAttribute("message", "El material ha sido guardado con éxito");					
				}else if(nameValid != null &&nameValid.getStatus() == 0){
					material.setStatus(1);
					service.updateMaterials(nameValid.getId(), material);
					redirectAttributes.addFlashAttribute("message", "El material ha sido guardado con éxito.");
				}else {
					redirectAttributes.addFlashAttribute("message", "El material ya existe.");	
				}

			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/materials";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/materials";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public Material formEditMaterial(@PathVariable Long id) {
		Material material = service.getById(id);
		return material;
	}

	@PutMapping("edit/{id}")
	public String editMaterials(@PathVariable Long id, @Valid @ModelAttribute Material materials, 
		   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
				Material materialsUpdate = service.getById(id);
				if (materialsUpdate != null) {
					materials.setInventory(materialsUpdate.getInventory());
					service.updateMaterials(id, materials);
					redirectAttributes.addFlashAttribute("message", "MATERIAL MODIFICADO EXITOSAMENTE");
				} else {
					redirectAttributes.addFlashAttribute("message", "EL MATERIAL NO EXISTE");
				}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", bindingResult.getFieldError().getDefaultMessage());
		}
		return "redirect:/materials";
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public boolean deleteMaterial(@PathVariable Long id) {
		boolean result = service.deleteMaterials(id);
		if (result) {
			return true;
		} else {
			return false;
		}
	}
}
