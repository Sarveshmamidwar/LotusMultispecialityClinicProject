package com.Hospital.entity;

import java.util.Arrays;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class StockInvoice {
	
	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id;
	private String EmployeeName;
	private long stockAmount;
	private String StockUpdateDate;
	private int employeeid;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] report;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmployeeName() {
		return EmployeeName;
	}
	public void setEmployeeName(String employeeName) {
		EmployeeName = employeeName;
	}
	public long getStockAmount() {
		return stockAmount;
	}
	public void setStockAmount(long stockAmount) {
		this.stockAmount = stockAmount;
	}
	public String getStockUpdateDate() {
		return StockUpdateDate;
	}
	public void setStockUpdateDate(String stockUpdateDate) {
		StockUpdateDate = stockUpdateDate;
	}
	public byte[] getReport() {
		return report;
	}
	public void setReport(byte[] report) {
		this.report = report;
	}

	public int getEmployeeid() {
		return employeeid;
	}
	public void setEmployeeid(int employeeid) {
		this.employeeid = employeeid;
	}
	
	public StockInvoice(int id, String employeeName, long stockAmount, String stockUpdateDate, int employeeid,
			byte[] report) {
		super();
		this.id = id;
		EmployeeName = employeeName;
		this.stockAmount = stockAmount;
		StockUpdateDate = stockUpdateDate;
		this.employeeid = employeeid;
		this.report = report;
	}
	public StockInvoice() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "StockInvoice [id=" + id + ", EmployeeName=" + EmployeeName + ", stockAmount=" + stockAmount
				+ ", StockUpdateDate=" + StockUpdateDate + ", employeeid=" + employeeid + ", report="
				+ Arrays.toString(report) + "]";
	}

}
