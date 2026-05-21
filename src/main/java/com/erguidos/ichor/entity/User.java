package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class User {
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @OneToOne(mappedBy = "userDischarged")
    private DischargedUser dischargedUser;
    
    protected User() {}
    
    public Long getId() { return this.id; }
    
    public String getUser() { return this.username; }
    
    public String getPassword() { return this.password; }

	public String getUsername() { return username; }

	public DischargedUser getDischargedUser() { return dischargedUser; }
}
