package com.example.rh.services;

import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.User;
import com.example.rh.payload.request.PersonnelRequest;

public interface IUserService {
	//public boolean effectuerPostulation(Long id );
	public List<User> getUsers();
	//public List<OffreEmploi> getPostulation(Long id) ;
	public void supprimerUser(Long id);
	public User updateUser(PersonnelRequest personnel,Long id);
}
