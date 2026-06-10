package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.DischargedStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "discharged_users")
public class DischargedUser {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    @OneToOne
    @JoinColumn(name = "id_user", unique = true, nullable = false)
	private User userDischarged;
    
    @Column(name = "discharged_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private DischargedStatus dischargedStatus;
	
	protected DischargedUser() {}
    
	public Long getId() { return this.id; }

	public User getUserDischarged() { return userDischarged; }

	public DischargedStatus getDischargedStatus() { return dischargedStatus; }
}
