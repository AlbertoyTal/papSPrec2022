package org.alberto.papRec2022.repository;

import org.alberto.papRec2022.entities.Pais;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaisRepository extends JpaRepository<Pais, Long>{
	public Pais findByNombre(String nombre);
}
