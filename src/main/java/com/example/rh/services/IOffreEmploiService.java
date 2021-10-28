package com.example.rh.services;

import java.util.Collection;

import com.example.rh.entities.OffreEmploi;

public interface IOffreEmploiService {
	public OffreEmploi ajouterOffre(OffreEmploi offre);
	public OffreEmploi modifierOffre(OffreEmploi offre , Long id);
	public void supprimerOffre(Long id);
	public Collection<OffreEmploi> getOffres();
	public OffreEmploi getOffreItem(Long id);
	public boolean candidatExiste(Long idOffre , Long idCandidat);
	public Collection<OffreEmploi> lesOffresDe(Long idUser);

}
