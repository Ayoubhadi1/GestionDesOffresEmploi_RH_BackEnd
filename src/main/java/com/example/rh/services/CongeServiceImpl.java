package com.example.rh.services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.rh.entities.Conge;
import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.User;
import com.example.rh.repository.CongeRepository;

@Service @Transactional
public class CongeServiceImpl implements ICongeService {
	
	@Autowired
	private CongeRepository congeRepository ;

	@Override
	public Conge ajouterConge(Conge conge) {
		return congeRepository.save(conge);
	}

	@Override
	public Conge modifierConge(Conge conge, Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void supprimerConge(Long id) {
		congeRepository.deleteById(id);
		
	}

	@Override
	public Collection<Conge> getConges() {
		return congeRepository.findAll();
	}

	@Override
	public Conge getCongeItem(Long id) {
		return congeRepository.findById(id).get();
	}

	@Override
	public Collection<Conge> lesCongesDe(Long idUser) {
		Collection<Conge> listConges = congeRepository.findAll();
		Collection<Conge> listDeCeConges = new ArrayList<Conge>();
		for(Conge c : listConges) {
			if(c.getEmploye().getId() == idUser) {
				listDeCeConges.add(c);
			}
		}
		
		return listDeCeConges;
	}

	@Override
	public Conge accepterConge(Long id) {
		Conge c = congeRepository.findById(id).get();
		c.setEtat("accepte");
		return congeRepository.save(c);
	}

	@Override
	public Conge refuserConge(Long id) {
		Conge c = congeRepository.findById(id).get();
		c.setEtat("refuse");
		return congeRepository.save(c);
	}
	

}
