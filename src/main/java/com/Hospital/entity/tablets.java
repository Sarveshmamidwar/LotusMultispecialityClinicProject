package com.Hospital.entity;

import org.springframework.web.bind.annotation.PutMapping;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class tablets {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	
	private String tabletName;
	private String medecineType;
	private String medicineCompanyName;
	private int price;
	private int quantity;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTabletName() {
		return tabletName;
	}

	public void setTabletName(String tabletName) {
		this.tabletName = tabletName;
	}
	
	public String getMedecineType() {
		return medecineType;
	}

	public void setMedecineType(String medecineType) {
		this.medecineType = medecineType;
	}

	public String getMedicineCompanyName() {
		return medicineCompanyName;
	}

	public void setMedicineCompanyName(String medicineCompanyName) {
		this.medicineCompanyName = medicineCompanyName;
	}
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public tablets() {
		super();
		// TODO Auto-generated constructor stub
	}
	

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public tablets(int id, String tabletName, String medecineType, String medicineCompanyName, int price,
			int quantity) {
		super();
		this.id = id;
		this.tabletName = tabletName;
		this.medecineType = medecineType;
		this.medicineCompanyName = medicineCompanyName;
		this.price = price;
		this.quantity = quantity;
	}

	@Override
	public String toString() {
		return "tablets [id=" + id + ", tabletName=" + tabletName + ", medecineType=" + medecineType
				+ ", medicineCompanyName=" + medicineCompanyName + ", price=" + price + ", quantity=" + quantity + "]";
	}

	

	
}
