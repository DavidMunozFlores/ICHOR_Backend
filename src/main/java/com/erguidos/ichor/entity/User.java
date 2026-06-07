package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.error.Errors;

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
    	this.setUsername(username);
    	this.setPassword(password);
    }
    
    public abstract Role getRole();
    
    public Long getId() { return this.id; }
    
    public String getPassword() { return this.password; }

	public String getUsername() { return this.username; }

	public DischargedUser getDischargedUser() { return dischargedUser; }

	private void setUsername(String username) {
	    if (username == null) {
	        throw Errors.User.NULL_USERNAME.asException();
	    }
		if (username.isBlank()) {
		    throw Errors.User.BLANK_USERNAME.asException();
		}
		this.username = username;
	}

	private void setPassword(String password) {
	    if (password == null) {
            throw Errors.User.NULL_PASSWORD.asException();
        }
        if (password.isBlank()) {
            throw Errors.User.BLANK_PASSWORD.asException();
        }
		this.password = password;
	}
}
