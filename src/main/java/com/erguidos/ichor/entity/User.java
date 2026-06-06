package com.erguidos.ichor.entity;

import com.erguidos.ichor.enums.Role;
import com.erguidos.ichor.error.Errors;
import com.erguidos.ichor.utils.RegexMatching;

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
    	this.setUsername(username);
    	this.setPassword(password);
    }
    
    public abstract Role getRole();
    
    public Long getId() { return this.id; }
    
    public String getUser() { return this.username; }
    
    public String getPassword() { return this.password; }

	public String getUsername() { return this.username; }

	public DischargedUser getDischargedUser() { return dischargedUser; }

	private void setUsername(String username) {
	    if (username == null) {
	        throw Errors.User.NULL_USERNAME.asException();
	    }
	    
		if(username.isEmpty())
			throw new IllegalArgumentException(EMPTY_USERNAME_MSJ);
		
		if (! RegexMatching.isValidUsername(username)) {
		    throw Errors.User.INVALID_USERNAME.asException();
		}
		this.username = username;
	}

	private void setPassword(String password) {
	    if (password == null) {
            throw Errors.User.NULL_PASSWORD.asException();
        }
	        
		if(password.isEmpty())
			throw new IllegalArgumentException(EMPTY_PASSWORD_MSJ);
		

        if (! RegexMatching.isValidUsername(password)) {
            throw Errors.User.INVALID_PASSWORD.asException();
        }
		this.password = password;
	}
}
