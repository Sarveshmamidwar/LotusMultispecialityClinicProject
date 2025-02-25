package com.Hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Doctors {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id ;
	private String Name;
	private String email;
	private String Password;
	private String Gender;
	private String Role;
	private String Address;
	private int doctorId;
	private String employeetype;
	private String mobileNumber;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return Name;
	}
	public void setName(String name) {
		Name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		email = email;
	}
	public String getPassword() {
		return Password;
	}
	public void setPassword(String password) {
		Password = password;
	}
	public String getGender() {
		return Gender;
	}
	public void setGender(String gender) {
		Gender = gender;
	}
	public String getRole() {
		return Role;
	}
	public void setRole(String role) {
		Role = role;
	}	
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	
	public String getEmployeetype() {
		return employeetype;
	}
	public void setEmployeetype(String employeetype) {
		this.employeetype = employeetype;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
	}
	public Doctors() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Doctors(int id, String name, String email, String password, String gender, String role, String address,
			int doctorId, String employeetype, String mobileNumber) {
		super();
		this.id = id;
		Name = name;
		this.email = email;
		Password = password;
		Gender = gender;
		Role = role;
		Address = address;
		this.doctorId = doctorId;
		this.employeetype = employeetype;
		this.mobileNumber = mobileNumber;
	}
	@Override
	public String toString() {
		return "Doctors [id=" + id + ", Name=" + Name + ", email=" + email + ", Password=" + Password + ", Gender="
				+ Gender + ", Role=" + Role + ", Address=" + Address + ", doctorId=" + doctorId + ", employeetype="
				+ employeetype + ", mobileNumber=" + mobileNumber + "]";
	}
	
	
	
	
	
	
}
