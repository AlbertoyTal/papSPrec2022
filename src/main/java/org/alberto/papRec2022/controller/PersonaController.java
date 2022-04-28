package org.alberto.papRec2022.controller;

import java.util.List;

import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
import org.alberto.papRec2022.service.AficionService;
import org.alberto.papRec2022.service.PaisService;
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
	
	@Autowired
	private PaisService paisService;

	@Autowired
	private AficionService aficionService;

	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view", "persona/r");
		m.put("personas", personaService.findAll());
		return "_t/frame";
	}

	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("paises", paisService.findAll());
		m.put("aficiones", aficionService.findAll());
		m.put("view", "persona/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("loginname") String loginname, 
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido,
			@RequestParam(value="idPaisNace",required = false) Long idPaisnace,
			@RequestParam(value="idPaisVive",required = false) Long idPaisVive,
			@RequestParam(value="idsAficionGusta[]",required = false )  Long[] idsAficionGusta,
			@RequestParam(value="idsAficionOdia[]",required = false) Long[] idsAficionOdia
			) throws DangerException 
	{
		try {
			personaService.save(loginname, nombre, apellido, idPaisnace, idPaisVive, idsAficionGusta, idsAficionOdia);
		} catch (Exception e) {
			if (e.getMessage().contains("UK_loginname")) {
				PRG.error("El loginname "+loginname+" ya existe","/persona/c");
			}
			else {
				PRG.error("Error desconocido al guardar la persona "+e.getMessage(),"/persona/c");
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
		m.put("paises", paisService.findAll());
		m.put("aficiones", aficionService.findAll());
		m.put("view", "persona/u");
		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idPersona") Long idPersona, 
			@RequestParam("loginname") String loginname, 
			@RequestParam("nombre") String nombre,
			@RequestParam("apellido") String apellido,
			@RequestParam(value="idPaisNace",required = false) Long idPaisNace) 
		throws DangerException

	{
		try {
			personaService.update(idPersona,loginname, nombre, apellido, idPaisNace);
			
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

	@GetMapping("d")
	public String dPost(
			@RequestParam(value="idPersona",required = false) Long idPersona
			) throws DangerException
			{
		if (idPersona==null) {
			PRG.error("El id de la persona a borrar no puede ser nulo","persona/r");
		}
		try {
			personaService.delete(idPersona);
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"persona/r");
		}
		return "redirect:/persona/r";
	}
}
