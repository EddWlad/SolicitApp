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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tidsec.gestion_solicitudes.entities.Company;
import com.tidsec.gestion_solicitudes.service.ICompanyService;


import jakarta.validation.Valid;


@Controller
@RequestMapping("/companies")
public class CompanyController {
	
	@Autowired
	private ICompanyService service;
	
	//@Autowired
	//private IRequestService requestService;
	
	@GetMapping
	public String listCompanies(Model model) {
		List<Company> company = service.getAll();
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String nombreUsuario = authentication.getName();
		//Usuarios usuario = usuariosService.findByCorreo(nombreUsuario);
		model.addAttribute("activoCompany", "active");
		//model.addAttribute("nombreRol", usuario.getRoles().getNombre());
		//model.addAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());
		model.addAttribute("listCompanies", company);
		model.addAttribute("company", new Company());
		return "companies";
	}
	
	@PostMapping("/save")
	public String saveCompany(@Valid @ModelAttribute Company company, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model, @RequestParam("file") MultipartFile imagen) {
		if (!bindingResult.hasErrors()) {
			try {
				Company rucValido = service.findByRuc(company.getRuc());
				if(rucValido==null) {
					//Company companySaved = service.saveCompany(company);
					//List<Request> requests = requestService.findByCompany(companySaved);
					//companySaved.setRequests(requests);
					service.saveCompany(company);
					redirectAttributes.addFlashAttribute("message", "EMPRESA CREADA EXITOSAMENTE");					
				}else if(rucValido != null &&rucValido.getStatus() == 0){
					company.setStatus(1);
					service.updateCompany(rucValido.getId(), company);
					redirectAttributes.addFlashAttribute("message", "EMPRESA CREADA EXITOSAMENTE");
				}else {
					redirectAttributes.addFlashAttribute("message", "EMPRESA EXISTENTE");	
				}

			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/companies";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/companies";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public Company formEditCompany(@PathVariable Long id) {
		Company company = service.getById(id);
		return company;
	}

	@PutMapping("edit/{id}")
	public String editCompany(@PathVariable Long id, @Valid @ModelAttribute Company company,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
					Company companyActual = service.getById(id);
					if (companyActual != null) {
						company.setRequests(companyActual.getRequests());
						service.updateCompany(id, company);
						redirectAttributes.addFlashAttribute("message", "EMPRESA MODIFICADA CON EXTIO");	
					}else {
					redirectAttributes.addFlashAttribute("message", "EMPRESA NO EXISTENTE");	
				}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
		}
		return "redirect:/companies";
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public boolean deleteCompany(@PathVariable Long id) {
		boolean result = service.deleteCompany(id);
		if (result) {
			return true;
		} else {
			return false;
		}
	}
	
}
