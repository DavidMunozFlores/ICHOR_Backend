package com.erguidos.ichor.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "discharged_users")
public class DischargedUser extends BaseEntity {
	
	
	private User userDischarged;
	
	protected DischargedUser() {}

	public User getUserDischarged() {
		return userDischarged;
	}
	
	
}
