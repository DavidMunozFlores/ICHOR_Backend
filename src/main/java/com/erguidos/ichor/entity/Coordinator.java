package com.erguidos.ichor.entity;

import java.util.List;

import com.erguidos.ichor.enums.Role;

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
    
    protected Coordinator(String username, String password, Hospital hospital) {
        super(username, password);
        this.setHospital(hospital);
    }
    
    @Override
    public Role getRole() {
        return Role.COORDINATOR;
    }

	public Hospital getHospital() { return this.hospital; }
	private void setHospital(Hospital hospital) {
	    this.hospital = hospital;
	    hospital.addCoordinator(this);
	}
	
	public List<Organ> getRegisteredOrgans() { return registeredOrgans; }

    public static CoordinatorBuilder builder() {
        return new CoordinatorBuilder();
    }

    public static class CoordinatorBuilder {
        private String username = null;
        private String password = null;
        private Hospital hospital = null;
        
        public CoordinatorBuilder() {}
        
        public CoordinatorBuilder setUsername(String username) {
            this.username = username;
            return this;
        }
        
        public CoordinatorBuilder setPassword(String password) {
            this.password = password;
            return this;
        }
        
        public CoordinatorBuilder setHospital(Hospital hospital) {
            this.hospital = hospital;
            return this;
        }
        
        public Coordinator build() {
            return new Coordinator(
                this.username,
                this.password,
                this.hospital
            );
        }
    }
}
