package com.erguidos.ichor.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "id_user")
public class Doctor extends User {
	
	@ManyToOne
	@JoinColumn(name="id_hospital", nullable = false)
	private Hospital hospital;
	
	@OneToMany(mappedBy = "doctor")
	private List<OrganPetition> organPetitions;
    
    protected Doctor() {
    	super();
    }
    
    private Doctor(String username, String password, Hospital hospital) {
    	super(username, password);
    	setHospital(hospital);
    	hospital.addDoctor(this);
    }
	
	public static Doctor create(String username, String password, Hospital hospital) {
		return new Doctor(username, password, hospital);
	}

	public List<OrganPetition> getOrganPetitions() { return organPetitions; }
	
	public Hospital getHospital() { return hospital; }
	
	private void setHospital(Hospital hospital) {
		this.hospital = hospital;
	}
}
