package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public abstract class WorksInHospital extends User {
	
	@ManyToOne
	@JoinColumn(name="id_hospital", nullable = false)
	private Hospital hopsital;
    
    protected WorksInHospital() {}

	public Hospital getHopsital() { return hopsital; }
}
