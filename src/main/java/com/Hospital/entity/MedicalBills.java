package com.Hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class MedicalBills {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String PatientName;
	private int PatientAge;
	private int PatientId;
	private String BillingDate;
	private String PatientAddress;
	private Double BillingAmount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getPatientName() {
		return PatientName;
	}
	public void setPatientName(String patientName) {
		PatientName = patientName;
	}
	public int getPatientAge() {
		return PatientAge;
	}
	public void setPatientAge(int patientAge) {
		PatientAge = patientAge;
	}
	public int getPatientId() {
		return PatientId;
	}
	public void setPatientId(int patientId) {
		PatientId = patientId;
	}
	public String getBillingDate() {
		return BillingDate;
	}
	public void setBillingDate(String billingDate) {
		BillingDate = billingDate;
	}
	public String getPatientAddress() {
		return PatientAddress;
	}
	public void setPatientAddress(String patientAddress) {
		PatientAddress = patientAddress;
	}
	public double getBillingAmount() {
		return BillingAmount;
	}
	public void setBillingAmount(Double billingAmount) {
		BillingAmount = billingAmount;
	}
	public MedicalBills(int id, String patientName, int patientAge, int patientId, String billingDate,
			String patientAddress, Double billingAmount) {
		super();
		this.id = id;
		PatientName = patientName;
		PatientAge = patientAge;
		PatientId = patientId;
		BillingDate = billingDate;
		PatientAddress = patientAddress;
		BillingAmount = billingAmount;
	}
	public MedicalBills() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "MedicalBills [id=" + id + ", PatientName=" + PatientName + ", PatientAge=" + PatientAge + ", PatientId="
				+ PatientId + ", BillingDate=" + BillingDate + ", PatientAddress=" + PatientAddress + ", BillingAmount="
				+ BillingAmount + "]";
	}
	
	
}
