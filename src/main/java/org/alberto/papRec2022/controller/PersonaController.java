package org.alberto.papRec2022.controller;

import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
import org.alberto.papRec2022.repository.PersonaRepository;
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
	private PersonaRepository personaRepository;

	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view", "persona/r");
		m.put("personas", personaRepository.findAll());
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
			personaRepository.save(persona);
			
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
}
