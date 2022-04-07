package org.alberto.papRec2022.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(name="UK_PaisNombre", columnNames = { "nombre" })
	}
)
public class Pais {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String nombre;
	
	@OneToMany(mappedBy = "nace")
	private Collection<Persona> nacidos;
	
	//=============================
	public Pais() {
		this.nacidos = new ArrayList<Persona>();
	}

	public Pais(String nombre) {
		super();
		this.nombre = nombre;
		this.nacidos = new ArrayList<Persona>();
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

	public Collection<Persona> getNacidos() {
		return nacidos;
	}

	public void setNacidos(Collection<Persona> nacidos) {
		this.nacidos = nacidos;
	}

	
	//=============================
}
