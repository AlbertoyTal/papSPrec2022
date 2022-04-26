package org.alberto.papRec2022.entities;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { 
		@UniqueConstraint(name="UK_loginname", columnNames = { "loginname" })
	}
)
public class Persona {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String loginname;
	private String nombre;
	private String apellido;
	
	@ManyToOne(optional = true)
	private Pais nace;

	@ManyToOne(optional = true)
	private Pais vive;
	
	@ManyToMany(mappedBy = "gustosos")
	private Collection<Aficion> gustos;
	
	@ManyToMany(mappedBy = "odiosos")
	private Collection<Aficion> odios;
	
	//=============================
	public Persona() {
		this.gustos= new ArrayList<Aficion>();
		this.odios= new ArrayList<Aficion>();
	}

	public Persona(String loginname, String nombre, String apellido) {
		super();
		this.loginname = loginname;
		this.nombre = nombre;
		this.apellido = apellido;
		this.gustos= new ArrayList<Aficion>();
		this.odios= new ArrayList<Aficion>();

	}

	//=============================

	
	public Long getId() {
		return id;
	}

	public Pais getVive() {
		return vive;
	}

	public void setVive(Pais vive) {
		this.vive = vive;
	}

	public Collection<Aficion> getGustos() {
		return gustos;
	}

	public void setGustos(Collection<Aficion> gustos) {
		this.gustos = gustos;
	}

	public Collection<Aficion> getOdios() {
		return odios;
	}

	public void setOdios(Collection<Aficion> odios) {
		this.odios = odios;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLoginname() {
		return loginname;
	}

	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Pais getNace() {
		return nace;
	}

	public void setNace(Pais nace) {
		this.nace = nace;
	}
	
	
	//=============================
}
