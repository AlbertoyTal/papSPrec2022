package org.alberto.papRec2022.controller;

import org.alberto.papRec2022.entities.Pais;
import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
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
@RequestMapping("/pais")
public class PaisController {

	@Autowired
	private PaisService paisService;
	
	@GetMapping("r")
	public String r(ModelMap m) {
		m.put("view", "pais/r");
		m.put("paises", paisService.findAll());
		return "_t/frame";
	}

	@GetMapping("c")
	public String c(ModelMap m) {
		m.put("view", "pais/c");
		return "_t/frame";
	}

	@PostMapping("c")
	public String cPost(
			@RequestParam("nombre") String nombre
			) 
					throws DangerException 
	{
		try {
			Pais pais = new Pais(nombre);
			paisService.save(pais);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_PaisNombre")) {
				PRG.error("El país "+nombre+" ya existe","/pais/c");
			}
			else {
				PRG.error("Error desconocido al guardar el país ","/pais/c");
			}
		}
		return "redirect:/pais/r";
	}
	
	@GetMapping("u")
	public String u(
			ModelMap m,
			@RequestParam(value="idPais", required = false) Long idPais
			) throws DangerException {
		if (idPais==null) {
			PRG.error("El id del país no puede ser nulo","/persona/r");
		}
		m.put("pais", paisService.getById(idPais));
		m.put("view", "pais/u");
		return "_t/frame";
	}

	@PostMapping("u")
	public String uPost(
			@RequestParam("idPais") Long idPais, 
			@RequestParam("nombre") String nombre
			) throws DangerException 
	{
		try {
			paisService.update(idPais,nombre);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_PaisNombre")) {
				PRG.error("El país "+nombre+" ya existe","/pais/u?idPais="+idPais);
			}
			else {
				PRG.error("Error desconocido al guardar el país ","/pais/u?idPais="+idPais);
			}
		}
		return "redirect:/pais/r";
	}

	@GetMapping("d")
	public String dPost(
			@RequestParam(value="idPais",required = false) Long idPais
			) throws DangerException
			{
		if (idPais==null) {
			PRG.error("El id del país a borrar no puede ser nulo","pais/r");
		}
		try {
			paisService.delete(idPais);
		}
		catch (Exception e) {
			PRG.error(e.getMessage(),"pais/r");
		}
		return "redirect:/pais/r";
	}
}
