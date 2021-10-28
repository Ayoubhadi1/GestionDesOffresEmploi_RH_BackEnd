package com.example.rh.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.User;
import com.example.rh.repository.OffreEmploiRepository;
import com.example.rh.repository.UserRepository;

@Service
@Transactional
public class OffreEmploiServiceImp implements IOffreEmploiService{
	
	@Autowired
	private OffreEmploiRepository offreEmploiRepository;
	
	@Autowired
	private UserRepository userRepository ;

	@Override
	public OffreEmploi ajouterOffre(OffreEmploi offre) {
		return offreEmploiRepository.save(offre);
	}

	@Override
	public OffreEmploi modifierOffre(OffreEmploi offre, Long id) {
		OffreEmploi offreAncien = offreEmploiRepository.findById(id).get();
		offreAncien.setTitre(offre.getTitre());
		offreAncien.setDescription(offre.getDescription());
		offreAncien.setContrat(offre.getContrat());
		offreAncien.setDateDebut(offre.getDateDebut());
		offreAncien.setEtat(offre.getEtat());
		return offreAncien;
	}

	@Override
	public void supprimerOffre(Long id) {
		offreEmploiRepository.deleteById(id);
		
	}

	@Override
	public Collection<OffreEmploi> getOffres() {
		// TODO Auto-generated method stub
		return offreEmploiRepository.findAll();
	}

	@Override
	public OffreEmploi getOffreItem(Long id) {
		return offreEmploiRepository.findById(id).get();
	}

	@Override
	public boolean candidatExiste(Long idOffre, Long idCandidat) {
		OffreEmploi offre = offreEmploiRepository.findById(idOffre).get();

		if(offre.getCandidats().size() == 0) return false ;
		for(User c: offre.getCandidats() ) {
			if(c.getId() == idCandidat) return true ;
		}
		
		return false ;
	}
	
	public Collection<OffreEmploi> lesOffresDe(Long idUser){
		Collection<OffreEmploi> listOffres = offreEmploiRepository.findAll();
		Collection<OffreEmploi> listDeCeCandidat = new ArrayList<OffreEmploi>();
		for(OffreEmploi o : listOffres) {
			if(this.candidatExiste(o.getId(), idUser)) {
				for(User c: o.getCandidats()) {
					if(c.getId() == idUser) {
						listDeCeCandidat.add(o);
					}
				}
			}
		}
		
		return listDeCeCandidat;
	}

}
