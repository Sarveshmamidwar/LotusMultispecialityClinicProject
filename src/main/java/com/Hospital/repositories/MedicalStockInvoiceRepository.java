package com.Hospital.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Hospital.entity.StockInvoice;

public interface MedicalStockInvoiceRepository extends JpaRepository<StockInvoice, Integer>{

}
