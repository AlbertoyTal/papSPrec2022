package org.alberto.papRec2022.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "UK_AficionNombre", columnNames = { "nombre" }) })
public class Aficion {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String nombre;

	@ManyToMany
	private Collection<Persona> gustosos;

	@ManyToMany
	private Collection<Persona> odiosos;

	// =============================
	public Aficion() {
		this.gustosos = new ArrayList<Persona>();
		this.odiosos = new ArrayList<Persona>();
	}

	public Aficion(String nombre) {
		super();
		this.nombre = nombre;
		this.gustosos = new ArrayList<Persona>();
		this.odiosos = new ArrayList<Persona>();
	}

	// =============================

	public Long getId() {
		return id;
	}

	public Collection<Persona> getGustosos() {
		return gustosos;
	}

	public void setGustosos(Collection<Persona> gustosos) {
		this.gustosos = gustosos;
	}

	public Collection<Persona> getOdiosos() {
		return odiosos;
	}

	public void setOdiosos(Collection<Persona> odiosos) {
		this.odiosos = odiosos;
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

	// =============================

	@Override
	public boolean equals(Object otraAficion) {
		return ((Aficion) otraAficion).getId().equals(this.id);
	}
}
