package org.alberto.papRec2022.repository;

import org.alberto.papRec2022.entities.Persona;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonaRepository extends JpaRepository<Persona, Long>{
}
