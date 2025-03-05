package com.Hospital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.entity.Reports;


public interface reportsrepository  extends JpaRepository<Reports, Integer>{

	
	@Query(value = "SELECT * FROM reports WHERE patientid = :id ", nativeQuery = true)
	List<Reports> findreportsbypatientid(@Param("id") int id);
}
