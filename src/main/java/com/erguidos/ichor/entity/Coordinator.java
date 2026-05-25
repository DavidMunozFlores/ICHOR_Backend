package com.erguidos.ichor.entity;

import java.util.List;

import com.erguidos.ichor.service.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "coordinators")
@PrimaryKeyJoinColumn(name = "id_user")
public class Coordinator extends User {
	
	@ManyToOne
	@JoinColumn(name="id_hospital", nullable = false)
	private Hospital hospital;
	
	@OneToMany(mappedBy = "coordinator")
	private List<Organ> registeredOrgans;
    
    protected Coordinator() {}
    
    @Override
    public Role getRole() {
        return Role.COORDINATOR;
    }

	public Hospital getHospital() { return hospital; }

	public List<Organ> getRegisteredOrgans() { return registeredOrgans; }
}
