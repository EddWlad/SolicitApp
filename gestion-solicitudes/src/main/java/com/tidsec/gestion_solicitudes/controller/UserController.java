package com.tidsec.gestion_solicitudes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
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

import com.tidsec.gestion_solicitudes.entities.Role;
import com.tidsec.gestion_solicitudes.entities.User;
import com.tidsec.gestion_solicitudes.service.IRoleService;
import com.tidsec.gestion_solicitudes.service.IUserService;


import jakarta.validation.Valid;

@Controller
@RequestMapping("/users")
public class UserController {
	@Autowired
	private IUserService service;
	@Autowired
	private IRoleService rolesService;

	@GetMapping
	public String listUser(Model model) {
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //String nombreUsuario = authentication.getName();
        //Usuarios usuario = service.findByCorreo(nombreUsuario);
		List<User> users = service.getAll();
		List<Role> rols = rolesService.getAll();		
		model.addAttribute("activoUsers", "active");
		//model.addAttribute("nameRol", user.getRole().getName();
		//model.addAttribute("nombreUsuario", usuario.getNombre()+" "+usuario.getApellido());
		model.addAttribute("listUsers", users);
		model.addAttribute("users", new User());
		model.addAttribute("listRols", rols);
		model.addAttribute("rols", new Role());
		return "users";
	}
	
	@PostMapping("/save")
	public String saveUser(@Valid @ModelAttribute User user, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				User emailValid = service.findByEmail(user.getEmail());
				if(emailValid == null ) {
					User cedulaValid = service.findByIdentification(user.getIdentification());
					if(cedulaValid == null) {
						service.saveUser(user);
						redirectAttributes.addFlashAttribute("message", "USUARIO CREADO EXITOSAMENTE");											
					}else if(cedulaValid != null && cedulaValid.getStatus() ==0){
						user.setStatus(1);
						service.updateUser(emailValid.getId(), user);
						redirectAttributes.addFlashAttribute("message", "USUARIO CREADO EXITOSAMENTE");						
					}else {
						redirectAttributes.addFlashAttribute("message", "CEDULA EXISTENTE");		
					}
				}else if(emailValid != null &&emailValid.getStatus() == 0){
					user.setStatus(1);
					service.updateUser(emailValid.getId(), user);
					redirectAttributes.addFlashAttribute("message", "USUARIO CREADO EXITOSAMENTE");	
				}else {
					redirectAttributes.addFlashAttribute("message", "EMAIL EXISTENTE");		
				}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/users";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/users";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public User formEditUser(@PathVariable Long id) {
		User user = service.getUser(id);
		return user;
	}

	@PutMapping("/edit/{id}")
	public String updaterUser(@PathVariable Long id, @Valid @ModelAttribute User user,
			BindingResult bindingResult, RedirectAttributes redirectAttributes) {
		if (!bindingResult.hasErrors()) {
			try {
				User updateUser = service.getUser(id);
				if(updateUser != null ) {
					user.setRole(updateUser.getRole());
					service.updateUser(id, user);
					redirectAttributes.addFlashAttribute("message", "USUARIO MODIFICADO EXITOSAMENTE");		
					}else {
						redirectAttributes.addFlashAttribute("message", "USUARIO EXISTENTE");		
					}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/users";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/users";
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public boolean deleteUsuario(@PathVariable Long id) {
		boolean result = service.deleteUser(id);
		if (result) {
			return true;
		} else {
			return false;
		}
	}
}
