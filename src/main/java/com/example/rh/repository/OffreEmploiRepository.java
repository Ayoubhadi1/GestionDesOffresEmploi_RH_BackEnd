package com.example.rh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

import com.example.rh.entities.OffreEmploi;
import com.example.rh.entities.User;


@RepositoryRestResource
@Repository
public interface OffreEmploiRepository extends JpaRepository<OffreEmploi, Long> {
	

}
