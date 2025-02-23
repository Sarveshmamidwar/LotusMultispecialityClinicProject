package com.Hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class appointmentform {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name ;
	private String gender;
	private String AppointmentDate;
	private String AppointmentTime;
	private String phone;
	private String doctor;
	private String email;
	private String message;
	private String AppointmentStatus;
	private String PaymentStatus;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getAppointmentDate() {
		return AppointmentDate;
	}
	public void setAppointmentDate(String appointmentDate) {
		AppointmentDate = appointmentDate;
	}
	public String getAppointmentTime() {
		return AppointmentTime;
	}
	public void setAppointmentTime(String appointmentTime) {
		AppointmentTime = appointmentTime;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getDoctor() {
		return doctor;
	}
	public void setDoctor(String doctor) {
		this.doctor = doctor;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getAppointmentStatus() {
		return AppointmentStatus;
	}
	public void setAppointmentStatus(String appointmentStatus) {
		AppointmentStatus = appointmentStatus;
	}	
	
	public String getPaymentStatus() {
		return PaymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		PaymentStatus = paymentStatus;
	}
	
	public appointmentform(int id, String name, String gender, String appointmentDate, String appointmentTime,
			String phone, String doctor, String email, String message, String appointmentStatus, String paymentStatus) {
		super();
		this.id = id;
		this.name = name;
		this.gender = gender;
		AppointmentDate = appointmentDate;
		AppointmentTime = appointmentTime;
		this.phone = phone;
		this.doctor = doctor;
		this.email = email;
		this.message = message;
		AppointmentStatus = appointmentStatus;
		PaymentStatus = paymentStatus;
	}
	public appointmentform() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "appointmentform [id=" + id + ", name=" + name + ", gender=" + gender + ", AppointmentDate="
				+ AppointmentDate + ", AppointmentTime=" + AppointmentTime + ", phone=" + phone + ", doctor=" + doctor
				+ ", email=" + email + ", message=" + message + ", AppointmentStatus=" + AppointmentStatus
				+ ", PaymentStatus=" + PaymentStatus + "]";
	}
	
	
	
	
	
	
}
