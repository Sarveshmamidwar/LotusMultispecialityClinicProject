package com.Hospital.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class staff {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private int id ;
	private String name;
	private String number;
	private String gender;
	private String email;
	private String address;
	private String empType;
	private int docid;
	
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
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getEmpType() {
		return empType;
	}
	public void setEmpType(String empType) {
		this.empType = empType;
	}
	
	public int getDocid() {
		return docid;
	}
	public void setDocid(int docid) {
		this.docid = docid;
	}
	
	public staff() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public staff(int id, String name, String number, String gender, String email, String address, String empType,
			int docid) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
		this.gender = gender;
		this.email = email;
		this.address = address;
		this.empType = empType;
		this.docid = docid;
	}
	@Override
	public String toString() {
		return "staff [id=" + id + ", name=" + name + ", number=" + number + ", gender=" + gender + ", email=" + email
				+ ", address=" + address + ", empType=" + empType + ", docid=" + docid + "]";
	}
	
	
	
}
