package com.erguidos.ichor.entity;

import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "users")
public abstract class User {
	private static final String EMPTY_USERNAME_MSJ = "Username cannot be empty";
	private static final String EMPTY_PASSWORD_MSJ = "Password cannot be empty";
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "username", unique = true, nullable = false, length = 255)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @OneToOne(mappedBy = "userDischarged")
    private DischargedUser dischargedUser;
    
    protected User() {}
    
    protected User(String username, String password) {
    	Objects.requireNonNull(username);
    	Objects.requireNonNull(password);
    	setUsername(username);
    	setPassword(password);
    }
    
    
    public Long getId() { return this.id; }
    
    public String getUser() { return this.username; }
    
    public String getPassword() { return this.password; }

	public String getUsername() { return username; }

	public DischargedUser getDischargedUser() { return dischargedUser; }

	private void setUsername(String username) {
		if(username.isEmpty())
			throw new IllegalArgumentException(EMPTY_USERNAME_MSJ);
		this.username = username;
	}

	private void setPassword(String password) {
		if(password.isEmpty())
			throw new IllegalArgumentException(EMPTY_PASSWORD_MSJ);
		this.password = password;
	}
	
	
}
