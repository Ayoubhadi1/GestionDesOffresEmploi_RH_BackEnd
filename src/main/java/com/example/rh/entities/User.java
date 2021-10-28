package com.example.rh.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(	name = "users", 
		uniqueConstraints = { 
			@UniqueConstraint(columnNames = "username"),
			@UniqueConstraint(columnNames = "email") 
		})
public class User implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles", 
				joinColumns = @JoinColumn(name = "user_id"), 
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;
	
	private String image ;
	private boolean active;
	
	private String domaine ;
	private String poste ;
	private int salaire;
	
	@OneToMany(mappedBy = "employe")
	private Collection<Conge> listeConges;
	
	
	 @ManyToMany(fetch = FetchType.LAZY,
	            cascade = {
	                CascadeType.PERSIST,
	                CascadeType.MERGE
	            },
	            mappedBy = "candidats")
	private Collection<OffreEmploi> offresRequests ;
	 
	@OneToMany(mappedBy = "responsable")
	private Collection<OffreEmploi> offresCreated ;
	 

	public User() {
	}
	
	public User(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password) {
		
		this.username = username;
		this.email = email;
		this.password = password;
		
	}
	
	
	

	public User(Long id, @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password, Set<Role> roles, String image, boolean active, String domaine,
			String poste, int salaire) {
		super();
		this.id = id;
		this.username = username;
		this.email = email;
		this.password = password;
		this.roles = roles;
		this.image = image;
		this.active = active;
		this.domaine = domaine;
		this.poste = poste;
		this.salaire = salaire;
	}
	
	public User( @NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) @Email String email,
			@NotBlank @Size(max = 120) String password,  String image,  String domaine,
			String poste, int salaire) {
		super();
		
		this.username = username;
		this.email = email;
		this.password = password;
		
		this.image = image;
		
		this.domaine = domaine;
		this.poste = poste;
		this.salaire = salaire;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public Set<Role> getRoles() {
		return roles;
	}
	
	

	public void setRoles(Set<Role> roles) {
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


