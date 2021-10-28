package com.example.rh.payload.request;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

public class CongeRequest {
	
	private String titre;
	private String description;
	private Date dateDebut;
	private Date dateFin;
	private String Etat;
	
}
