package com.Hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.entity.staff;

public interface HositalStaffrepository extends JpaRepository<staff, Integer> {

}
