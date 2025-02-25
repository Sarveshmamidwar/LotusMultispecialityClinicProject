package com.Hospital.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.Hospital.entity.Doctors;

@Repository
public interface DoctorsRepositories extends JpaRepository<Doctors, Integer>{

	Doctors findByEmail(String email);
	
	@Query(value = "SELECT * FROM doctors WHERE email = :email", nativeQuery = true)
	List<Doctors> findByemails(@Param("email") String Email);
	
	@Query(value = "SELECT * FROM doctors WHERE doctor_id = :id", nativeQuery = true)
	List<Doctors> findemployeebydoctors(@Param("id") int doctorId);
}
