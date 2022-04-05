package org.alberto.papRec2022.controller;

import org.alberto.papRec2022.entities.Aficion;
import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
import org.alberto.papRec2022.service.AficionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/aficion")
public class AficionController {

	@Autowired
	private AficionService aficionService;
	
	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view", "aficion/r");
		m.put("aficiones", aficionService.findAll());
		return "_t/frame";
	}

	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "aficion/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam(value="nombre",required=false) String nombre
			) 
					throws DangerException 
	{
		if (nombre==null || nombre.equals("")) {
			PRG.error("El nombre de la afición no puede ser nulo","/aficion/c");
		}
		try {
			Aficion aficion = new Aficion(nombre);
			aficionService.save(aficion);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_AficionNombre")) {
				PRG.error("La afición "+nombre+" ya existe","/aficion/c");
			}
			else {
				PRG.error("Error desconocido al guardar la afición ","/pais/c");
			}
		}
		return "redirect:/aficion/r";
	}
	
	@GetMapping("u")
	public String u(
			ModelMap m,
			@RequestParam(value="idAficion", required = false) Long idAficion
			) throws DangerException {
		if (idAficion==null) {
			PRG.error("El id de la afición no puede ser nulo","/aficion/r");
		}
		m.put("aficion", aficionService.getById(idAficion));
		m.put("view", "aficion/u");
		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam(value="idAficion",required=false) Long idAficion, 
			@RequestParam(value="nombre",required=false) String nombre
			) throws DangerException 
	{
		try {
			aficionService.update(idAficion,nombre);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_AficionNombre")) {
				PRG.error("La afición "+nombre+" ya existe","/aficion/u?idAficion="+idAficion);
			}
			else if (e.getMessage().contains("nulo")) {
				PRG.error(e.getMessage(),"/aficion/u?idAficion="+idAficion);
			}
			else {
				PRG.error("Error desconocido al guardar la afición ","/aficion/u?idAficion="+idAficion);
			}
		}
		return "redirect:/aficion/r";
	}

	@GetMapping("d")
	public String dPost(
			@RequestParam(value="idAficion",required = false) Long idAficion
			) throws DangerException
			{
		if (idAficion==null) {
			PRG.error("El id de la afición a borrar no puede ser nulo","aficion/r");
		}
		try {
			aficionService.delete(idAficion);
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"aficion/r");
		}
		return "redirect:/aficion/r";
	}
}
