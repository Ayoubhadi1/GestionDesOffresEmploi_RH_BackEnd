package com.example.rh.controllers;

import org.springframework.http.ResponseEntity;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.rh.entities.Conge;
import com.example.rh.entities.ERole;
import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.Role;
import com.example.rh.entities.User;
import com.example.rh.payload.request.PersonnelRequest;
import com.example.rh.payload.response.MessageResponse;
import com.example.rh.repository.OffreEmploiRepository;
import com.example.rh.repository.RoleRepository;
import com.example.rh.repository.UserRepository;
import com.example.rh.security.services.UserDetailsImpl;
import com.example.rh.services.IOffreEmploiService;
import com.example.rh.services.ICongeService;
import com.example.rh.services.IUserService;

import antlr.collections.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/gestionRH")
public class TestController {
	
	@Autowired
	private OffreEmploiRepository offreEmploiRepository;
	
	@Autowired
	private UserRepository userRepository ;
	
	@Autowired
	private RoleRepository rolesRepository ;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	private IOffreEmploiService offreService ;
	
	@Autowired
	private IUserService userService;
	
	@Autowired
	private ICongeService congeService;
	
	@GetMapping("/all")
	public String allAccess() {
		return "Public Content.";
	}
	
	@GetMapping("/roles")
	public String[] roles() {
		
	    return ERole.names() ;
	}
	
	@GetMapping("/user")
	@PreAuthorize("hasRole('USER') or hasRole('EMPLOYE') or hasRole('ADMIN')")
	public String userAccess() {
		return "User Content.";
	}
	
	@GetMapping("/offreEmploi")
	public Collection<OffreEmploi> listOffresUser() {
		return offreService.getOffres();
	}
	
	@GetMapping("/offreEmploi/{id}")
	  OffreEmploi one(@PathVariable Long id){
	    
	    return offreService.getOffreItem(id);
	      
	  }
	
	@PostMapping("/user/postulerOffreEmploi/{id}")
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> postulerOffre(@PathVariable Long id)  throws IllegalArgumentException {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Long idUser = userDetails.getId();
		
		if (offreService.candidatExiste(id, idUser)) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: vous avez déjà postuler à cet offre!"));
		}
		
		User currentUser = userRepository.findById(idUser).get();
		OffreEmploi offre = offreEmploiRepository.findById(id).get();
		offre.addCandidat(currentUser);
		return ResponseEntity.ok(offreEmploiRepository.save(offre));
		
	}
	
	@GetMapping("/user/getOffres/{id}")
	@PreAuthorize("hasRole('USER')")
	public Collection<OffreEmploi> getOffresForCandidat(@PathVariable Long id) {
		return offreService.lesOffresDe(id);
	}
	
	@GetMapping("/employe")
	@PreAuthorize("hasRole('EMPLOYE')")
	public String moderatorAccess() {
		return "Employe Board.";
	}
	
	@PostMapping("/employe/demanderConge")
	@PreAuthorize("hasRole('EMPLOYE')")
	public Conge ajouterConge(@RequestBody Conge conge ) {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Long idUser = userDetails.getId();
		User u = userRepository.findById(idUser).get();
		conge.setEmploye(u);
		return congeService.ajouterConge(conge);
	}
	
	
	
	@GetMapping("/employe/conges")
	@PreAuthorize("hasRole('EMPLOYE')")
	public Collection<Conge> getCongeDe() {
		UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal();
		Long idUser = userDetails.getId();
		return congeService.lesCongesDe(idUser);
	}
	
	@GetMapping("/employe/conge/{id}")
	@PreAuthorize("hasRole('EMPLOYE')")
	public Conge getCongeDetail(@PathVariable Long id) {
		return congeService.getCongeItem(id);
	}
	

	@GetMapping("/admin")
	@PreAuthorize("hasRole('ADMIN')")
	public String adminAccess() {
		return "Admin Board.";
	}
	
	@GetMapping("/admin/users")
	@PreAuthorize("hasRole('ADMIN') or hasRole('EMPLOYE')")
	public java.util.List<User> allUsers(){
		return userService.getUsers();
	}
	
	@GetMapping("/admin/user/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	public User getOneUser(@PathVariable Long id){
		return userRepository.findById(id).get();
	}
	
	
	@PostMapping("/admin/ajouterPersonnel")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> ajouterPersonnel(@Valid @RequestBody PersonnelRequest personnel ) {
		
		if (userRepository.existsByUsername(personnel.getUsername())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(personnel.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		
		/*Set<Role> roles2 = new HashSet<Role>();
		for(String r : personnel.getRoles()) {
			if(r.equals("admin")) {
				Role rl = new Role(); 
				rl.setName(ERole.ROLE_ADMIN);
				roles2.add(rl);
			}
			if(r.equals("employe")) {
				Role rl = new Role(); 
				rl.setName(ERole.ROLE_EMPLOYE);
				roles2.add(rl);
			}
			
			System.out.println(roles2);
			
		}*/
		User user = new User(personnel.getUsername(), 
				 personnel.getEmail(),
				 encoder.encode(personnel.getPassword()),
				 personnel.getImage(),
				 personnel.getDomaine(),
				 personnel.getPoste(),
				 personnel.getSalaire()
				);
		user.setActive(true);

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

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!") );
	}
	
	
	
	@PutMapping("/admin/user/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	  public User updateUser(@RequestBody PersonnelRequest personnel, @PathVariable Long id) {
		return userService.updateUser(personnel, id);
		
	}
	 
	 @DeleteMapping("/admin/user/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	  public void deleteUser(@PathVariable Long id) {
	    userService.supprimerUser(id);
	  }
	 
	 @PostMapping("/admin/ajouterOffre")
		@PreAuthorize("hasRole('ADMIN')")
		public OffreEmploi ajouterOffre(@RequestBody OffreEmploi newOffre ) {
			UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication()
	                .getPrincipal();
			Long id = userDetails.getId();
			newOffre.setResponsable(userRepository.findById(id).get());
			return offreService.ajouterOffre(newOffre);
		}
	
	 @PutMapping("/admin/offreEmploi/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	  public OffreEmploi updateOffre(@RequestBody OffreEmploi newOffre, @PathVariable Long id) {
	    
	    return offreService.modifierOffre(newOffre, id);
	  }
	 
	 @DeleteMapping("/admin/offreEmploi/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	  public void deleteOffre(@PathVariable Long id) {
	    offreService.supprimerOffre(id);
	  }
	 
	 @GetMapping("/admin/offreEmploi")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Collection<OffreEmploi> listOffresAdmin() {
			return offreService.getOffres();
		}
	 
	 @GetMapping("/admin/conges")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Collection<Conge> listCongesAdmin() {
			return congeService.getConges();
		}
	 
	 @PutMapping("/admin/accepterConge/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Conge accepterConge(@PathVariable Long id ) {
			
				return congeService.accepterConge(id);
			
			
		}
	 
	 @PutMapping("/admin/refuserConge/{id}")
	 @PreAuthorize("hasRole('ADMIN')")
	 public Conge refuserConge(@PathVariable Long id ) {
			
				return congeService.refuserConge(id);
			
			
		}
	
	
}