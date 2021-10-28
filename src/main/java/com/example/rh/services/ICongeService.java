package com.example.rh.services;

import java.util.Collection;

import com.example.rh.entities.Conge;
import com.example.rh.entities.OffreEmploi;

public interface ICongeService {
	public Conge ajouterConge(Conge conge);
	public Conge modifierConge(Conge conge , Long id);
	public void supprimerConge(Long id);
	public Collection<Conge> getConges();
	public Conge getCongeItem(Long id);
	public Collection<Conge> lesCongesDe(Long idUser);
	public Conge accepterConge(Long id);
	public Conge refuserConge(Long id);
 
}
