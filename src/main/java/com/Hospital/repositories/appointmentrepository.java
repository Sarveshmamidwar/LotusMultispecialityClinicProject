package com.Hospital.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.entity.appointmentform;

public interface appointmentrepository  extends JpaRepository<appointmentform, Integer>{
	
	
	@Query(value = "SELECT * FROM appointmentform WHERE appointment_date = CURRENT_DATE", nativeQuery = true)
	List<appointmentform> findTodayAppointments();

	@Query(value = "SELECT * FROM appointmentform WHERE appointment_status = 'Cancle' ", nativeQuery = true)
	List<appointmentform> findCancleAppointments();

	@Query(value = "SELECT * FROM appointmentform WHERE appointment_date = CURRENT_DATE + INTERVAL 1 DAY", nativeQuery = true)
	List<appointmentform> findTomorrowAppointments();
	
	@Query(value = "SELECT count(*) FROM appointmentform ", nativeQuery = true)
	 int  findtotalcount();

	@Query(value = "SELECT count(*) FROM appointmentform WHERE appointment_date = CURRENT_DATE", nativeQuery = true)
	int findTodayAppointmentsCount();
	
	@Query(value = "SELECT count(*) FROM appointmentform WHERE appointment_status = 'Cancle' ", nativeQuery = true)
	int findCancleAppointmentsCount();
	
	@Query(value = "SELECT count(*) FROM appointmentform WHERE appointment_date = CURRENT_DATE + INTERVAL 1 DAY", nativeQuery = true)
	int findTomorrowAppointmentsCount();
	
	@Query(value = "SELECT * FROM appointmentform WHERE appointment_date = CURRENT_DATE and appointment_status = 'Pending' ", nativeQuery = true)
	List<appointmentform> findTodayAppointmentswithstatus();

	@Query(value = "SELECT * FROM appointmentform WHERE id = :id", nativeQuery = true)
    appointmentform findbyappointmentid(@Param("id") int id);
	
	@Query(value = "SELECT * FROM appointmentform WHERE patientid = :id ", nativeQuery = true)
	List<appointmentform> findAppointmentsbypatientid(@Param("id") int id);
}
