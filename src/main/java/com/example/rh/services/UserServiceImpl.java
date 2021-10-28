package com.example.rh.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.rh.entities.ERole;
import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.Role;
import com.example.rh.entities.User;
import com.example.rh.payload.request.PersonnelRequest;
import com.example.rh.repository.OffreEmploiRepository;
import com.example.rh.repository.UserRepository;
import com.example.rh.repository.RoleRepository;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private OffreEmploiRepository offreRepository ;
	
	@Autowired
	private RoleRepository rolesRepository;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	public List<User> getUsers() {
		return userRepository.findAll();
	}

	@Override
	public void supprimerUser(Long id) {
		userRepository.deleteById(id);
		
	}

	@Override
	public User updateUser(PersonnelRequest personnel, Long id) {
		User user1 = userRepository.findById(id).get();
		user1.setUsername(personnel.getUsername());
		user1.setEmail(personnel.getEmail());
		user1.setPassword(encoder.encode(personnel.getPassword()));
		user1.setImage(personnel.getImage());
		user1.setDomaine(personnel.getDomaine());
		user1.setPoste(personnel.getPoste());
		user1.setSalaire(personnel.getSalaire());
		user1.setActive(true);
		

		Set<String> strRoles = personnel.getRoles();
		Set<Role> roles = new HashSet<>();
		
		if (strRoles == null) {
			Role userRole = rolesRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = rolesRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "employe":
					Role modRole = rolesRepository.findByName(ERole.ROLE_EMPLOYE)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = rolesRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user1.setRoles(roles);
		return userRepository.save(user1);
	}
	
	/*@Override
	public List<OffreEmploi> getPostulation(Long id) {
		return userRepository.findById(id).get();
	}*/
	
	

}
