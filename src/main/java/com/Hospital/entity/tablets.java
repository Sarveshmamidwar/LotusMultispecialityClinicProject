package com.Hospital.entity;

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

	public tablets() {
		super();
		// TODO Auto-generated constructor stub
	}

	public tablets(int id, String tabletName, String medecineType, String medicineCompanyName) {
		super();
		this.id = id;
		this.tabletName = tabletName;
		this.medecineType = medecineType;
		this.medicineCompanyName = medicineCompanyName;
	}

	@Override
	public String toString() {
		return "tablets [id=" + id + ", tabletName=" + tabletName + ", medecineType=" + medecineType
				+ ", medicineCompanyName=" + medicineCompanyName + "]";
	}

	
	
	
	

}
