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
	public Doctors() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Doctors(int id, String name, String email, String password, String gender, String role) {
		super();
		this.id = id;
		Name = name;
		email = email;
		Password = password;
		Gender = gender;
		Role = role;
	}
	@Override
	public String toString() {
		return "Doctors [id=" + id + ", Name=" + Name + ", Email=" + email + ", Password=" + Password + ", Gender="
				+ Gender + ", Role=" + Role + "]";
	}
	
	
}
