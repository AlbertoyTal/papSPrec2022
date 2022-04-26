package org.alberto.papRec2022.service;

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

	public void update(Long idPersona, String loginname, String nombre, String apellido) throws Exception {
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
		personaRepository.saveAndFlush(persona);
	}

	
	public void update(Long idPersona, String loginname, String nombre, String apellido, Long idPaisNace) throws Exception {
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
		personaRepository.saveAndFlush(persona);
	}

	
	public void delete(Long idPersona) throws Exception {
		if (personaRepository.findById(idPersona) == null) {
			throw new Exception("El id de la persona " + idPersona + " no existe");
		}
		personaRepository.delete(personaRepository.getById(idPersona));
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
