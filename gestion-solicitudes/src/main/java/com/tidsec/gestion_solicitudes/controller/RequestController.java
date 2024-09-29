package com.tidsec.gestion_solicitudes.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Material;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.Request;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.model.RequestModalDTO;
import com.tidsec.gestion_solicitudes.service.ICompanyService;
import com.tidsec.gestion_solicitudes.service.IInventoryService;
import com.tidsec.gestion_solicitudes.service.IMaterialService;
import com.tidsec.gestion_solicitudes.service.IProjectService;
import com.tidsec.gestion_solicitudes.service.IRequestService;
import com.tidsec.gestion_solicitudes.service.IUserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/requests")
public class RequestController {
	@Autowired
	private IRequestService service;
	
	@Autowired
	private ICompanyService companyService;
	
	@Autowired
	private IProjectService projectService;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private IMaterialService materialService;
	
	@Autowired
	private IInventoryService inventoryService;
	
	@InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }
	
	@GetMapping
	public String listUser(Model model) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String nombreUsuario = authentication.getName();
        //Usuarios usuario = service.findByCorreo(nombreUsuario);
		model.addAttribute("activoRequest", "active");
		List<Request> requests = service.getAll();
		List<Company> companies = companyService.getAll();
		List<Project> projects = projectService.getAll();
		List<User> users = userService.getAll();

		//model.addAttribute("nameRol", user.getRole().getName();
		//model.addAttribute("nombreUsuario", usuario.getNombre()+" "+usuario.getApellido());
		model.addAttribute("lisRequest", requests);
		model.addAttribute("requests", new Request());
		model.addAttribute("listCompany", companies);
		model.addAttribute("companies", new Company());
		model.addAttribute("listProject", projects);
		model.addAttribute("projects", new Project());
		model.addAttribute("listUser", users);
		model.addAttribute("users", new User());
		return "requests";
	}
	
	@PostMapping("/save")
	public String saveRequest(@Valid @ModelAttribute Request request,@ModelAttribute Inventory inventory,@ModelAttribute Material materials, 
			@RequestParam("companies") Long companyId,@RequestParam("project") Long projectId, @RequestParam("inventory") Long inventoryId,
			@RequestParam("materials") List<Long> materialsId, 
			@RequestParam("user") Long userId,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
					Optional<Company> company = companyService.findById(companyId);
					company.ifPresent(request::setCompany);
					
					Optional<Project> project = projectService.findById(projectId);
					project.ifPresent(request::setProject);
					
					Optional<User> user = userService.findById(userId);
					user.ifPresent(request::setRequester);
					
					List<Material> material = materialService.buscarPorIds(materialsId);
					inventory.setMaterial(new ArrayList<>(material));
					
					Inventory inventories = inventoryService.findById(inventoryId);
					materials.setInventory(inventories);
					
					service.saveRequest(request);
					materialService.saveMaterial(materials);
					inventoryService.saveInventory(inventory);
					redirectAttributes.addFlashAttribute("message", "SOLICITUD CREADA Y ENVIADA CON EXITO");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/requests";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/requests";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public RequestModalDTO formularioEditarEtiqueta(@PathVariable Long id) {
		RequestModalDTO  request = service.detailRequestById(id).get(0);

		return request;
	}

	@PutMapping("/edit/{id}")
	public String editRequest(Model model,@PathVariable Long id, @ModelAttribute Request request,@PathVariable Long idInventory,@ModelAttribute Inventory inventory,@PathVariable Long idMaterials, @ModelAttribute Material materials,
			@RequestParam("company") Long companyId,@RequestParam("project") Long projectId,
			@RequestParam("user") Long userId,@RequestParam("inventory") Long inventoryId,
			@RequestParam("materials") List<Long> materialsId,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
				Optional<Company> company = companyService.findById(companyId);
				company.ifPresent(request::setCompany);
				
				Optional<Project> project = projectService.findById(projectId);
				project.ifPresent(request::setProject);
				
				Optional<User> user = userService.findById(userId);
				user.ifPresent(request::setRequester);
				
				List<Material> material = materialService.buscarPorIds(materialsId);
				inventory.setMaterial(new ArrayList<>(material));
				
				Inventory inventories = inventoryService.findById(inventoryId);
				materials.setInventory(inventories);
				
				service.updateRequest(id, request);
				inventoryService.updateInventory(idInventory, inventory);
				materialService.updateMaterials(idMaterials, materials);
				redirectAttributes.addFlashAttribute("message", "LA SOLICITUD HA SIDO MODIFICADA CORRECTAMENTE");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/requests";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/requests";
		}
	}
	
	@DeleteMapping("/delete/{id}")
	public boolean deleteRequest(@PathVariable Long id) {
		boolean result = service.deleteRequest(id);
		if (result) {
			return true;
		} else {
			return false;
		}
	}
	
}
