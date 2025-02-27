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
	private String name;
	private String email;
	private String password;
	private String gender;
	private String role;
	private String address;
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
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getDoctorId() {
		return doctorId;
	}
	public void setDoctorId(int doctorId) {
		this.doctorId = doctorId;
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
	public Doctors(int id, String name, String email, String password, String gender, String role, String address,
			int doctorId, String employeetype, String mobileNumber) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.gender = gender;
		this.role = role;
		this.address = address;
		this.doctorId = doctorId;
		this.employeetype = employeetype;
		this.mobileNumber = mobileNumber;
	}
	public Doctors() {
		super();
		// TODO Auto-generated constructor stub
	}
	@Override
	public String toString() {
		return "Doctors [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", gender="
				+ gender + ", role=" + role + ", address=" + address + ", doctorId=" + doctorId + ", employeetype="
				+ employeetype + ", mobileNumber=" + mobileNumber + "]";
	}
	
	
	
	
	
	
}
