package com.tidsec.gestion_solicitudes.controller;

import java.text.SimpleDateFormat;
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
import com.tidsec.gestion_solicitudes.entities.Project;
import com.tidsec.gestion_solicitudes.entities.Request;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.service.ICompanyService;
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
	public String saveRequest(@Valid @ModelAttribute Request request, @RequestParam("companies") Long companyId,@RequestParam("project") Long projectId,
			@RequestParam("user") Long userId,BindingResult bindingResult,RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
					Optional<Company> company = companyService.findById(companyId);
					company.ifPresent(request::setCompany);
					
					Optional<Project> project = projectService.findById(projectId);
					project.ifPresent(request::setProject);
					
					Optional<User> user = userService.findById(userId);
					user.ifPresent(request::setRequester);
					
					service.saveRequest(request);
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
	
	//@GetMapping("/edit/{id}")
	//@ResponseBody
	//public EtiquetaModalDTO formularioEditarEtiqueta(@PathVariable Integer id) {
		//EtiquetaModalDTO  etiqueta = service.detalleEtiquetaPorId(id).get(0);

		//return etiqueta;
	//}

	@PutMapping("/edit/{id}")
	public String editRequest(Model model,@PathVariable Long id, @ModelAttribute Request request,@RequestParam("company") Long companyId,@RequestParam("project") Long projectId,
			@RequestParam("user") Long userId,BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
				Optional<Company> company = companyService.findById(companyId);
				company.ifPresent(request::setCompany);
				
				Optional<Project> project = projectService.findById(projectId);
				project.ifPresent(request::setProject);
				
				Optional<User> user = userService.findById(userId);
				user.ifPresent(request::setRequester);
				
				service.updateRequest(id, request);
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
