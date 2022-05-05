package org.alberto.papRec2022.controller.rest;

import java.util.List;


import org.alberto.papRec2022.entities.Aficion;
import org.alberto.papRec2022.exception.DangerException;
import org.alberto.papRec2022.exception.PRG;
import org.alberto.papRec2022.service.AficionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aficiones")
public class RestAficionController {

	@Autowired
	private AficionService aficionService;
	
	@GetMapping()
	public List<Aficion> r() {
		return aficionService.findAll();
	}

	@GetMapping("{id}")
	public Aficion rId(@PathVariable("id") Long id) {
		return aficionService.getById(id);
	}
	
	@PostMapping()
	public Aficion cPost(
			@RequestParam(value="nombre",required=false) String nombre
			) 
					throws DangerException 
	{
		if (nombre==null || nombre.equals("")) {
			PRG.error("El nombre de la afición no puede ser nulo","/aficion/c");
		}
		try {
			Aficion aficion = new Aficion(nombre);
			return aficionService.save(aficion);
			
		} catch (Exception e) {
			if (e.getMessage().contains("UK_AficionNombre")) {
				PRG.error("La afición "+nombre+" ya existe","/aficion/c");
				return null;
			}
			else {
				PRG.error("Error desconocido al guardar la afición ","/pais/c");
				return null;
			}
		}
	}
	
	@PatchMapping("{id}")
	public String uPost(
			@PathVariable(value="id") Long idAficion, 
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

	@DeleteMapping("{id}")
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
