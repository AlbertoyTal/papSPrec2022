package org.alberto.papRec2022.service;

import java.util.List;


import org.alberto.papRec2022.entities.Aficion;
import org.alberto.papRec2022.repository.AficionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AficionService {

	@Autowired
	private AficionRepository aficionRepository;

	public List<Aficion> findAll() {
		return aficionRepository.findAll();
	}

	public Aficion save(Aficion aficion) {
		return aficionRepository.save(aficion);
	}

	public Aficion getById(Long idAficion) {
		return aficionRepository.getById(idAficion);
	}

	public void update(Long idAficion, String nombre) throws Exception {
		if (idAficion==null || nombre==null || nombre.equals("")) {
			throw new Exception("El nombre de la afición o el id no pueden ser nulos");
		}
		Aficion aficion = aficionRepository.getById(idAficion);
		if (!nombre.equals(aficion.getNombre())) {
			if (aficionRepository.findByNombre(nombre) == null) {
				aficion.setNombre(nombre);
			} else {
				throw new Exception("UK_AficionNombre");
			}
		}
		aficion.setNombre(nombre);
		aficionRepository.saveAndFlush(aficion);
	}

	public void delete(Long idAficion) throws Exception {
		if (aficionRepository.findById(idAficion) == null) {
			throw new Exception("El id de la afición " + idAficion + " no existe");
		}
		aficionRepository.delete(aficionRepository.getById(idAficion));
	}
}
