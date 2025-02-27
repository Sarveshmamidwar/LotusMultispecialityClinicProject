package com.Hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Hospital.entity.Patient;

@Repository
public interface patientrepository extends JpaRepository<Patient, Integer> {

	Patient findByemail(String email);
	
	
}
