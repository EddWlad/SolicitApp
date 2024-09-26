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


import com.tidsec.gestion_solicitudes.entities.Role;
import com.tidsec.gestion_solicitudes.service.IRoleService;


import jakarta.validation.Valid;



@Controller
@RequestMapping("/rols")
public class RoleController {
	@Autowired
	private IRoleService service;
	//@Autowired
	//private IModuloService moduloService;
	//@Autowired
	//private IPermisosService permisosService;
	//@Autowired
	//private IUserService userService;
	

	@GetMapping
	public String listRols(Model model) {
		List<Role> rols = service.getAll();
		//Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		//String nombreUsuario = authentication.getName();
		//Usuarios usuario = usuariosService.findByCorreo(nombreUsuario);
		model.addAttribute("activoUsers", "active");
		//model.addAttribute("nombreRol", usuario.getRoles().getNombre());
		//model.addAttribute("nombreUsuario", usuario.getNombre() + " " + usuario.getApellido());
		model.addAttribute("listRol", rols);
		model.addAttribute("rols", new Role());
		return "rols";
	}
	
	@PostMapping("/save")
	public String saveRole(@Valid @ModelAttribute Role rol, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				Role nameValid = service.findByName(rol.getName());
				if(nameValid==null)
				{
					service.saveRole(rol);
					redirectAttributes.addFlashAttribute("message", "EL ROL HA SIDO GUARDADO CON EXITO");
				}else if(nameValid != null &&nameValid.getStatus() == 0)
				{
					rol.setStatus(1);
					service.updateRole(nameValid.getId(), rol);
					redirectAttributes.addFlashAttribute("message", "EL ROL HA SIDO GUARDADO CON EXITO");
				}
				else {
					redirectAttributes.addFlashAttribute("message", "EL ROL YA EXISTE");	
				}

			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/rols";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/rols";
		}
	}
	
	@GetMapping("/edit/{id}")
	@ResponseBody
	public Role FormEditRole(@PathVariable Long id) {
		Role rol = service.obtenerRole(id);
		return rol;
	}

	@PutMapping("edit/{id}")
	public String updateRol(@PathVariable Long id, @Valid @ModelAttribute Role rol, BindingResult bindingResult,
			RedirectAttributes redirectAttributes, Model model) {
		if (!bindingResult.hasErrors()) {
			try {
				service.updateRole(id, rol);
				redirectAttributes.addFlashAttribute("message", "EL ROL HA SIDO MODIFICADO CON EXITO");
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
			return "redirect:/rols";
		} else {
			redirectAttributes.addFlashAttribute("message",bindingResult.getFieldError().getDefaultMessage());
			return "redirect:/rols";
		}
	}
	
	@DeleteMapping("/delete/{id}")
	@ResponseBody
	public boolean deleteRol(@PathVariable Long id) {
		return service.deleteRole(id);
	}
	
	/*@GetMapping("/permisos/{id}")
	@ResponseBody
	public RolesPermisosDTO obtenerPermisos(@PathVariable Integer id) {

		List<Modulos> arrModulos = moduloService.obtenerTodas();
		List<Permisos> permisos = permisosService.obtenerPermisosPorIdRol(id);
		Roles role = rolesService.obtenerRol(id);

		Map<Integer, List<Permisos>> permitsByModule = permisos.stream().collect(Collectors.groupingBy(permiso -> {
			return permiso.getModulos() != null ? permiso.getModulos().getId() : 0;
		}));

		List<ModulosPermisosDto> modulePermissions = arrModulos.stream().map(module -> {
			List<Permisos> modulePermits = new ArrayList<>();
			if (permitsByModule.containsKey(module.getId())) {
				modulePermits = permitsByModule.get(module.getId());
			} else {
				Permisos permiso = new Permisos();
				permiso.setRead(false);
				permiso.setUpdate(false);
				permiso.setWrite(false);
				permiso.setDelete(false);
				modulePermits.add(permiso);
			}

			return new ModulosPermisosDto(module.getId(), module.getNombre(), modulePermits.get(0));
		}).collect(Collectors.toList());

		return new RolesPermisosDTO(role.getId(), role.getNombre(), modulePermissions);
	}

	@PostMapping(path = "/permisos/{id}", consumes = "application/json")
	public String actualizarPermisos(@PathVariable Integer id,@Valid @RequestBody PermisosDTO permiso,
			RedirectAttributes redirectAttributes) {

			try {
				boolean resultDelete = permisosService.eliminarPermisoPorRol(id);
				if (resultDelete) {
					List<PermisoDTO> permisosDto = permiso.getPermisos();
					Roles role = rolesService.obtenerRol(id);
					permisosDto.forEach(p -> {
						Modulos modulo = moduloService.obtenerPorId(p.getModulos_id());
						Permisos permisos = new Permisos();
						permisos.setModulos(modulo);
						permisos.setRoles(role);
						permisos.setRead(p.isRead());
						permisos.setWrite(p.isWrite());
						permisos.setUpdate(p.isUpdate());
						permisos.setDelete(p.isDelete());
						permisosService.crearPermiso(permisos);					
					});
					redirectAttributes.addFlashAttribute("message", "Los permisos se guardaron con éxito");
				} else {
					redirectAttributes.addFlashAttribute("message", "Error al crear los permisos");
				}
			} catch (Exception e) {
				redirectAttributes.addFlashAttribute("message", e.getMessage());
			}
		return "redirect:/roles";

	}*/
}
