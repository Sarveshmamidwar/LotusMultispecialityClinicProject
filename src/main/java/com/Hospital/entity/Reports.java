package com.Hospital.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class Reports {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String Appointmentdate;
	private int patientid;
	private int appointmentid;
	@Lob
	@Column(columnDefinition = "MEDIUMBLOB")
	private byte[] report;
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAppointmentdate() {
		return Appointmentdate;
	}
	public void setAppointmentdate(String appointmentdate) {
		Appointmentdate = appointmentdate;
	}
	public int getPatientid() {
		return patientid;
	}
	public void setPatientid(int patientid) {
		this.patientid = patientid;
	}
	public byte[] getReport() {
		return report;
	}
	public void setReport(byte[] report) {
		this.report = report;
	}
	
	
	public int getAppointmentid() {
		return appointmentid;
	}
	public void setAppointmentid(int appointmentid) {
		this.appointmentid = appointmentid;
	}
	
	public Reports() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Reports(int id, String appointmentdate, int patientid, int appointmentid, byte[] report) {
		super();
		this.id = id;
		Appointmentdate = appointmentdate;
		this.patientid = patientid;
		this.appointmentid = appointmentid;
		this.report = report;
	}
	
	
	
	
	
}
