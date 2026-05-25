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
    
    @Override
    public Role getRole() {
        return Role.MANAGER;
    }
}
