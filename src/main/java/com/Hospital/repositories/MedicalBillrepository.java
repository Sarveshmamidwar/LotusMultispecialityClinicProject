package com.Hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.entity.MedicalBills;

public interface MedicalBillrepository extends JpaRepository<MedicalBills, Integer> {

}
