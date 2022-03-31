package org.alberto.papRec2022.service;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.alberto.papRec2022.entities.Pais;
import org.alberto.papRec2022.entities.Persona;
import org.alberto.papRec2022.repository.PaisRepository;
import org.alberto.papRec2022.repository.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaisService {

	@Autowired
	private PaisRepository paisRepository;

	public List<Pais> findAll() {
		return paisRepository.findAll();
	}

	public void save(Pais pais) {
		paisRepository.save(pais);
	}

	public Pais getById(Long idPais) {
		return paisRepository.getById(idPais);
	}

	public void update(Long idPais, String nombre) throws Exception {
		Pais pais = paisRepository.getById(idPais);
		if (!nombre.equals(pais.getNombre())) {
			if (paisRepository.findByNombre(nombre) == null) {
				pais.setNombre(nombre);
			} else {
				throw new Exception("UK_PaisNombre");
			}
		}
		pais.setNombre(nombre);
		paisRepository.saveAndFlush(pais);
	}

	public void delete(Long idPais) throws Exception {
		if (paisRepository.findById(idPais) == null) {
			throw new Exception("El id del país " + idPais + " no existe");
		}
		paisRepository.delete(paisRepository.getById(idPais));
	}
}
