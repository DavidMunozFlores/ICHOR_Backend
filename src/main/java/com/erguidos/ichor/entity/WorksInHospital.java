package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class WorksInHospital
                extends User {
	
	@Column(name = "")
	private Hospital hopsital;
    
    protected WorksInHospital() {}
}
