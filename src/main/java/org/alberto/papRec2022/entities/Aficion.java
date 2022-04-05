package org.alberto.papRec2022.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(name="UK_AficionNombre", columnNames = { "nombre" })
	}
)
public class Aficion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	//=============================
	public Aficion() {
	}

	public Aficion(String nombre) {
		super();
		this.nombre = nombre;
	}

	//=============================

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	
	//=============================
}
