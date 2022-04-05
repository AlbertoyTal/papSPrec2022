package org.alberto.papRec2022.repository;

import org.alberto.papRec2022.entities.Aficion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AficionRepository extends JpaRepository<Aficion, Long>{
	public Aficion findByNombre(String nombre);
}
