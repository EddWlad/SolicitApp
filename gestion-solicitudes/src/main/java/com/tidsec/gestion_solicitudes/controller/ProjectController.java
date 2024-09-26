package com.tidsec.gestion_solicitudes.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidsec.gestion_solicitudes.entities.Inventory;
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.service.IInventoryService;
import com.tidsec.gestion_solicitudes.service.IProjectService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/projects")
public class ProjectController {
	@Autowired
	private IProjectService service;
	
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
		List<Project> projects = service.getAll();
		List<User> usersEnginners = service.findByRole();		
		model.addAttribute("activoProjects", "active");
		//model.addAttribute("nameRol", user.getRole().getName();
		//model.addAttribute("nombreUsuario", usuario.getNombre()+" "+usuario.getApellido());
		model.addAttribute("listProjects", projects);
		model.addAttribute("projects", new Project());
		model.addAttribute("listUser", usersEnginners);
		model.addAttribute("usersEnginners", new User());
		return "projects";
	}

	@PostMapping("/save")
	public String saveProject(@Valid @ModelAttribute Project project, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				Project nameProjectValid = service.projectName(project.getProjectName());
				if(nameProjectValid==null) {
					Project projectSaved = service.saveProject(project);
					//List<Inventory> inventory = inventoryService.findByProject(projectSaved);
					//projectSaved.setInventory(inventory);
					service.saveProject(project);
					redirectAttributes.addFlashAttribute("message", "PROYECTO CREADO CORRECTAMENTE");					
				}else if(nameProjectValid != null &&nameProjectValid.getStatus() == 0){
					project.setStatus(1);
					service.updateProject(nameProjectValid.getId(), project);
					redirectAttributes.addFlashAttribute("message", "PROYECTO CREADO CORRECTAMENTE");
				}else {
					redirectAttributes.addFlashAttribute("message", "PROYECTO EXISTENTE");	
				}

			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/projects";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/projects";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public Project formEditProject(@PathVariable Long id) {
		Project project = service.getProject(id);
		return project;
	}

	@PutMapping("edit/{id}")
	public String editProjects(@PathVariable Long id, @Valid @ModelAttribute Project project, 
		   BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
				Project projectUpdate = service.getProject(id);
				if (projectUpdate != null) {
					project.setInventory(projectUpdate.getInventory());
					service.updateProject(id,project);
					redirectAttributes.addFlashAttribute("message", "PROYECTO MODIFICADO EXITOSAMENTE");
				} else {
					redirectAttributes.addFlashAttribute("message", "EL PROYECTO NO EXISTE");
				}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message", bindingResult.getFieldError().getDefaultMessage());
		}
		return "redirect:/projects";
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public boolean deleteProject(@PathVariable Long id) {
		boolean result = service.deleteProject(id);
		if (result) {
			return true;
		} else {
			return false;
		}
	}
}
