package com.Hospital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.entity.appointmentform;
import com.Hospital.entity.tablets;

public interface tabletsrepositories extends JpaRepository<tablets, Integer> {

	@Query(value = "SELECT * FROM tablets WHERE LOWER(tablet_name) LIKE LOWER(CONCAT('%', :tabname, '%'))", nativeQuery = true)
	List<tablets> findtabletsbyname( @Param("tabname") String tabname);
	
	
	@Query(value = "SELECT * FROM tablets WHERE id=:id", nativeQuery = true)
	List<tablets> findtabletsbyid( @Param("id") int id);
}
