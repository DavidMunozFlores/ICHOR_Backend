package com.erguidos.ichor.entity;

import com.erguidos.ichor.service.Role;

import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "managers")
@PrimaryKeyJoinColumn(name = "id_user")
public class Manager extends User {
    
    protected Manager() {}
    
    private Manager(String username, String password) {
        super(username, password);
    }
    
    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
    
    public static ManagerBuilder builder() {
        return new ManagerBuilder();
    }
    
    public static class ManagerBuilder {
        private String username = null;
        private String password = null;
        
        public ManagerBuilder() {}
        
        public ManagerBuilder setUsername(String username) {
            this.username = username;
            return this;
        }
        
        public ManagerBuilder setPassword(String password) {
            this.password = password;
            return this;
        }
        
        public Manager build() {
            return new Manager(this.username, this.password);
        }
    }
}
