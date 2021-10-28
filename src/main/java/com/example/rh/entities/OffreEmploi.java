package com.example.rh.entities;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @NoArgsConstructor @AllArgsConstructor 
public class OffreEmploi implements Serializable{
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titre;
	private String description;
	private String contrat;
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-mm-dd")
	private Date dateDebut;
	private String etat;
	
	  @ManyToMany(fetch = FetchType.LAZY,
	            cascade = {
	                CascadeType.PERSIST,
	                CascadeType.MERGE
	            })
	    @JoinTable(name = "offre_candidat",
	    		joinColumns = @JoinColumn(name = "offre_id"), 
				inverseJoinColumns = @JoinColumn(name = "candidat_id"))
	private Collection<User> candidats ;
	  
	public boolean addCandidat(User candidat) {
		return this.candidats.add(candidat);
	}
	
	public boolean deleteCandidat(User candidat) {
		return this.candidats.remove(candidat);
	}
	  
	@ManyToOne
	private User responsable ;
}
