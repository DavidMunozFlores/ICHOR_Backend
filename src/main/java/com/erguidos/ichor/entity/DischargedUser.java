package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
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
    @JoinColumn(name = "id_user", nullable = false)
	private User userDischarged;
	
	protected DischargedUser() {}
    
	public Long getId() { return this.id; }

	public User getUserDischarged() { return userDischarged; }
}
