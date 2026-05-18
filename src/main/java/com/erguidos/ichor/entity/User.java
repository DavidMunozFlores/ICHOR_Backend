package com.erguidos.ichor.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class User
                extends BaseEntity {
    @Column(name = "username", nullable = false, length = 255)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    protected User() {}
    
    public String getUser() { return this.username; }
    
    public String getPassword() { return this.password; }
}
