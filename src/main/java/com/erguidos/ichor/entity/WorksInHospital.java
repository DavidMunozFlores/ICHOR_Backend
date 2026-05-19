package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class WorksInHospital
                extends User {
	
	@ManyToOne
	@JoinColumn(name="id_hospital")
	private Hospital hopsital;
    
    protected WorksInHospital() {}

	public Hospital getHopsital() {
		return hopsital;
	}
    
    
}
