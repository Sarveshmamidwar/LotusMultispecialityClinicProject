package com.Hospital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Hospital.entity.appointmentform;

public interface appointmentrepository  extends JpaRepository<appointmentform, Integer>{
	
	
	@Query(value = "SELECT * FROM appointmentform WHERE appointment_date = CURRENT_DATE", nativeQuery = true)
	List<appointmentform> findTodayAppointments();

	@Query(value = "SELECT * FROM appointmentform WHERE appointment_status = 'Cancle' ", nativeQuery = true)
	List<appointmentform> findCancleAppointments();

}
