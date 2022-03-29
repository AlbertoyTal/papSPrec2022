package org.alberto.papRec2022.controller;

import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
import org.alberto.papRec2022.service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/persona")
public class PersonaController {

	@Autowired
	private PersonaService personaService;
	
	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view", "persona/r");
		m.put("personas", personaService.findAll());
		return "_t/frame";
	}

	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "persona/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("loginname") String loginname, 
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido) throws DangerException 
	{
		try {
			Persona persona = new Persona(loginname, nombre, apellido);
			personaService.save(persona);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_loginname")) {
				PRG.error("El loginname "+loginname+" ya existe","/persona/c");
			}
			else {
				PRG.error("Error desconocido al guardar la persona ","/persona/c");
			}
		}
		return "redirect:/persona/r";
	}
	
	@GetMapping("u")
	public String u(
			ModelMap m,
			@RequestParam(value="idPersona", required = false) Long idPersona
			) throws DangerException {
		if (idPersona==null) {
			PRG.error("El id de la persona no puede ser nulo","/persona/r");
		}
		m.put("persona", personaService.getById(idPersona));
		m.put("view", "persona/u");
		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idPersona") Long idPersona, 
			@RequestParam("loginname") String loginname, 
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido) throws DangerException 
	{
		try {
			personaService.update(idPersona,loginname, nombre, apellido);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_loginname")) {
				PRG.error("El loginname "+loginname+" ya existe","/persona/u?idPersona="+idPersona);
			}
			else {
				PRG.error("Error desconocido al guardar la persona ","/persona/u?idPersona="+idPersona);
			}
		}
		return "redirect:/persona/r";
	}

}
