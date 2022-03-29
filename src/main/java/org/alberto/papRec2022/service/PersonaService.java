package org.alberto.papRec2022.service;

import java.util.List;

import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonaService {

	@Autowired
	private PersonaRepository personaRepository;

	public List<Persona> findAll() {
		return personaRepository.findAll();
	}
	
	public void save(Persona persona) {
		personaRepository.save(persona);
	}
	
	public Persona getById(Long idPersona) {
		return personaRepository.getById(idPersona);
	}

	public void update(Long idPersona, String loginname, String nombre, String apellido) throws Exception {
		Persona persona = personaRepository.getById(idPersona);
		if (!loginname.equals(persona.getLoginname())) {
			if (personaRepository.findByLoginname(loginname)==null) {
				persona.setLoginname(loginname);
			}
			else {
				throw new Exception("UK_loginname");
			}
		}
		persona.setNombre(nombre);
		persona.setApellido(apellido);
		personaRepository.saveAndFlush(persona);
	}
}
