package org.alberto.papRec2022.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.alberto.papRec2022.entities.Aficion;
import org.alberto.papRec2022.entities.Pais;
import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.repository.AficionRepository;
import org.alberto.papRec2022.repository.PaisRepository;
import org.alberto.papRec2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

	//=======================================================
	@Autowired
	private PersonaRepository personaRepository;
	
	@Autowired
	private PaisRepository paisRepository;

	@Autowired
	private AficionRepository aficionRepository;

	//=======================================================
	
	public List<Persona> findAll() {
		return personaRepository.findAll();
	}

	public Persona getById(Long idPersona) {
		return personaRepository.getById(idPersona);
	}
	
	public void update(Long idPersona, String loginname, String nombre, String apellido, 
			Long idPaisNace, Long idPaisVive,
			List<Long> idsAficionGusta,
			List<Long> idsAficionOdia
			) throws Exception {
		
		Persona persona = personaRepository.getById(idPersona);
		if (!loginname.equals(persona.getLoginname())) {
			if (personaRepository.findByLoginname(loginname) == null) {
				persona.setLoginname(loginname);
			} else {
				throw new Exception("UK_loginname");
			}
		}
		persona.setNombre(nombre);
		persona.setApellido(apellido);
	
		Pais paisNace = null;
		if (idPaisNace != null && idPaisNace>0) {
			paisNace = paisRepository.getById(idPaisNace);
		}
		persona.setNace(paisNace);

		Pais paisVive = null;
		if (idPaisVive != null && idPaisVive > 0) {
			paisVive = paisRepository.getById(idPaisVive);
		}
		persona.setVive(paisVive);

		
		idsAficionGusta = idsAficionGusta==null? new ArrayList<Long>() : idsAficionGusta;
		idsAficionOdia = idsAficionOdia ==null? new ArrayList<Long>() : idsAficionOdia;
		
		for (Aficion gusto : persona.getGustos()) {
			gusto.getGustosos().remove(persona);
			aficionRepository.saveAndFlush(gusto);
		}
		persona.getGustos().clear();
		personaRepository.saveAndFlush(persona);
		
		ArrayList<Aficion> gustosNuevos = new ArrayList<Aficion>();
		for (Long idAficionGusta : idsAficionGusta) {
			Aficion gusto = aficionRepository.getById(idAficionGusta);
			gusto.getGustosos().add(persona);
			gustosNuevos.add(gusto);
		}
		persona.setGustos(gustosNuevos);

		for (Aficion odio : persona.getOdios()) {
			odio.getOdiosos().remove(persona);
			aficionRepository.saveAndFlush(odio);
		}
		persona.getOdios().clear();
		personaRepository.saveAndFlush(persona);
		ArrayList<Aficion> odiosNuevos = new ArrayList<Aficion>();
		for (Long idAficionOdia: idsAficionOdia) {
			Aficion odio = aficionRepository.getById(idAficionOdia);
			odio.getOdiosos().add(persona);
			odiosNuevos.add(odio);
		}
		persona.setOdios(odiosNuevos);
		
		personaRepository.saveAndFlush(persona);
	}

	
	public void delete(Long idPersona) throws Exception {
		if (personaRepository.findById(idPersona) == null) {
			throw new Exception("El id de la persona " + idPersona + " no existe");
		}
		
		Persona persona = personaRepository.getById(idPersona); 

		Pais paisNacimiento = persona.getNace();
		paisNacimiento.getNacidos().remove(persona);
		paisRepository.saveAndFlush(paisNacimiento);
		persona.setNace(null);
		personaRepository.saveAndFlush(persona);
		
		Pais paisResidencia = persona.getVive();
		paisResidencia.getResidentes().remove(persona);
		paisRepository.saveAndFlush(paisResidencia);
		persona.setVive(null);
		personaRepository.saveAndFlush(persona);
		
		Collection<Aficion> gustos = persona.getGustos();
		for (Aficion gusto : gustos) {
			gusto.getGustosos().remove(persona);
			aficionRepository.saveAndFlush(gusto);
		}
		persona.getGustos().clear();
		personaRepository.saveAndFlush(persona);

		Collection<Aficion> odios = persona.getOdios();
		for (Aficion odio : odios) {
			odio.getOdiosos().remove(persona);
			aficionRepository.saveAndFlush(odio);
		}
		persona.getOdios().clear();
		personaRepository.saveAndFlush(persona);
		
		personaRepository.delete(persona);
		
	}

	public void save(String loginname, String nombre, String apellido) {
		personaRepository.save(new Persona(loginname,nombre,apellido));
	}
	
	public void save(String loginname, String nombre, String apellido, Long idPaisNace, Long idPaisVive, Long[] idsAficionGusta, Long[] idsAficionOdia) {
		Persona persona = new Persona(loginname,nombre,apellido);
		
		// Set Pais nace ===========================================
		Pais paisNace = null;
		if (idPaisNace != null && idPaisNace>0) {
			paisNace = paisRepository.getById(idPaisNace);
		}
		persona.setNace(paisNace);
		
		if (paisNace!=null) {
			paisNace.getNacidos().add(persona);
		}

		// Set Pais vive ===========================================
		Pais paisVive = null;
		if (idPaisVive != null && idPaisVive>0) {
			paisVive = paisRepository.getById(idPaisVive);
		}
		persona.setVive(paisVive);
		
		if (paisVive!=null) {
			paisVive.getResidentes().add(persona);
		}
		
		// ======================  Set gustos =======================
		
		if (idsAficionGusta != null ) {
			for (Long idAficionGusta : idsAficionGusta) {
				Aficion aficionGusta = aficionRepository.getById(idAficionGusta);
				persona.getGustos().add(aficionGusta);
				aficionGusta.getGustosos().add(persona);
			}
		}
		
		// ======================  Set odios =======================
		if (idsAficionOdia!=null) {
			for (Long idAficionOdia : idsAficionOdia) {
				Aficion aficionOdia = aficionRepository.getById(idAficionOdia);
				persona.getOdios().add(aficionOdia);
				aficionOdia.getOdiosos().add(persona);
			}
		}
		//=======================================
		
		personaRepository.save(persona);
	}
	
}
