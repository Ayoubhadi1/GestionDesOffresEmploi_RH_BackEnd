package com.example.rh.payload.request;

import java.util.Set;

import javax.validation.constraints.*;
 


import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.example.rh.entities.OffreEmploi;


public class PersonnelRequest{
	

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	private String password;

	private Set<String> roles ;
	
	private String image ;
	private boolean active;
	
	private String domaine ;
	private String poste ;
	private int salaire;
	
	
	 @ManyToMany(fetch = FetchType.LAZY,
	            cascade = {
	                CascadeType.PERSIST,
	                CascadeType.MERGE
	            },
	            mappedBy = "candidats")
	private Collection<OffreEmploi> offresRequests ;
	 
	@OneToMany(mappedBy = "responsable")
	private Collection<OffreEmploi> offresCreated ;
	 

	public PersonnelRequest() {
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<String> getRoles() {
		return roles;
	}
	
	

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}



	public String getImage() {
		return image;
	}



	public void setImage(String image) {
		this.image = image;
	}



	public boolean isActive() {
		return active;
	}



	public void setActive(boolean active) {
		this.active = active;
	}



	public String getDomaine() {
		return domaine;
	}



	public void setDomaine(String domaine) {
		this.domaine = domaine;
	}



	public String getPoste() {
		return poste;
	}



	public void setPoste(String poste) {
		this.poste = poste;
	}



	public int getSalaire() {
		return salaire;
	}



	public void setSalaire(int salaire) {
		this.salaire = salaire;
	}
	
	
}


