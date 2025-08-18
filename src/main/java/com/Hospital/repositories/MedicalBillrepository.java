package com.Hospital.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Hospital.entity.Doctors;
import com.Hospital.entity.MedicalBills;

public interface MedicalBillrepository extends JpaRepository<MedicalBills, Integer> {

	
	@Query(value = "SELECT sum(billing_amount) FROM medical_bills ", nativeQuery = true)
	int  findTotaRevenue();
}
